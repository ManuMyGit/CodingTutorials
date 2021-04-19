package org.mjjaen.microservices.eventdriven.kafka.twitterproducer.service.impl;

import lombok.extern.log4j.Log4j2;
import org.mjjaen.microservices.eventdriven.kafka.twitterproducer.businessObject.KafkaMessage;
import org.mjjaen.microservices.eventdriven.kafka.twitterproducer.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
@Log4j2
public class KafkaServiceImpl implements KafkaService {
    @Autowired
    private KafkaTemplate<String, KafkaMessage> kafkaTemplate;

    @Override
    public void sendMessage(String topic, String key, KafkaMessage message) {
        ListenableFuture<SendResult<String, KafkaMessage>> future;
        if(key == null)
            future = kafkaTemplate.send(topic, message);
        else
            future = kafkaTemplate.send(topic, key, message);
        future.addCallback(new ListenableFutureCallback<SendResult<String, KafkaMessage>>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.error("Error while writting data: ", throwable);
            }

            @Override
            public void onSuccess(SendResult<String, KafkaMessage> sendResult) {
                log.info("Tweet sent to Kafka! Message: " + sendResult.getProducerRecord().value().getText());
                log.info("Retweets of the tweet: " + sendResult.getProducerRecord().value().getRetweet_count());
                log.info("Topic: " + sendResult.getRecordMetadata().topic());
                log.info("Partition: " + sendResult.getRecordMetadata().partition());
                log.info("Offset: " + sendResult.getRecordMetadata().offset());
            }
        });
        kafkaTemplate.flush();
    }
}
