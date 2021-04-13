package org.mjjaen.microservices.eventdriven.kafka.consumer.consumers;

import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.mjjaen.microservices.eventdriven.kafka.consumer.businessObject.MyMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class KafkaConsumers {
    @KafkaListener(topics = "my_topic")
    public void readMessage(ConsumerRecord<String, MyMessage> record) {
        log.info("KL Data read ...");
        log.info("KL Data: " + record.value());
        log.info("KL Topic: " + record.topic());
        log.info("KL Partition: " + record.partition());
        log.info("KL Offset: " + record.offset());
        log.info("KL Timestamp: " + record.timestamp());
        log.info("KL Key: " + record.key());
    }
}