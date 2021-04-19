package org.mjjaen.microservices.eventdriven.kafka.twitterproducer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twitter.hbc.core.Client;
import lombok.extern.slf4j.Slf4j;
import org.mjjaen.microservices.eventdriven.kafka.twitterproducer.businessObject.KafkaMessage;
import org.mjjaen.microservices.eventdriven.kafka.twitterproducer.businessObject.Tweet;
import org.mjjaen.microservices.eventdriven.kafka.twitterproducer.configuration.TwitterConfiguration;
import org.mjjaen.microservices.eventdriven.kafka.twitterproducer.service.KafkaService;
import org.mjjaen.microservices.eventdriven.kafka.twitterproducer.service.TwitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PreDestroy;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@Slf4j
public class TwitterProducerApplication implements CommandLineRunner {
    @Autowired
    private TwitterService twitterService;

    @Autowired
    private KafkaService kafkaService;

    @Autowired
    private TwitterConfiguration twitterConfiguration;

    private Client client;

    public static void main( String[] args )
    {
        SpringApplication.run(TwitterProducerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        BlockingQueue<String> msgQueue = twitterService.createMessageQueue(100000);
        client = twitterService.createTwitterClient(msgQueue, "new tork", "boston", "doncic", "tatum");
        twitterService.connect(client);
        try {
            while (!client.isDone()) {
                String message = msgQueue.poll(500, TimeUnit.MILLISECONDS);
                if(message != null) {
                    ObjectMapper om = new ObjectMapper();
                    Tweet tweet = om.readValue(message, Tweet.class);
                    log.info("Twitter read: " + tweet.getText());
                    kafkaService.sendMessage(twitterConfiguration.getKafkaTopic(), null, KafkaMessage.builtKafkaMessageFromTweet(tweet));
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } finally {
            twitterService.stop(client);
        }
    }

    @PreDestroy
    public void closeTwitterConnection() {
        log.info("Closing Twitter client ...");
        twitterService.stop(client);
        log.info("Twitter client closed");
    }
}
