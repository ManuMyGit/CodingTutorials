package org.mjjaen.microservices.eventdriven.kafka.twitterstreams.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.mjjaen.microservices.eventdriven.kafka.twitterstreams.businessObject.KafkaMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
        @PropertySource("classpath:application.properties")
})
@Log4j2
public class KafkaProcessor {
    @Value("${topic.name.in}")
    private String topicIn;

    @Value("${topic.name.out}")
    private String topicOut;

    @Value("${number.of.retweets}")
    private int numberOfRetweets;

    @Bean
    public KStream<String, String> kStream(StreamsBuilder kStreamBuilder) {
        KStream<String, String> stream = kStreamBuilder.stream(topicIn);
        stream.filter((k , v) ->  {
            boolean matches = false;
            KafkaMessage kafkaMessage = null;
            try {
                ObjectMapper om = new ObjectMapper();
                kafkaMessage = om.readValue(v, KafkaMessage.class);
                matches = kafkaMessage.getRetweet_count() >= numberOfRetweets;
            } catch (JsonProcessingException e) {
                log.error("Error parsing " + v, e);
                e.printStackTrace();
            }
            if(matches)
                log.info("Twitter matched: " + kafkaMessage.getText());
            return matches;
        }).to(topicOut);
        return stream;
    }
}
