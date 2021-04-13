package org.mjjaen.microservices.eventdriven.kafka.consumer.consumers;

import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.mjjaen.microservices.eventdriven.kafka.consumer.businessObject.MyMessage;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class MyKafkaConsumer implements AcknowledgingMessageListener<String, MyMessage> {
    @Override
    public void onMessage(ConsumerRecord consumerRecord, Acknowledgment acknowledgment) {
        try {
            log.info("MKL Data read ...");
            log.info("MKL Data: " + consumerRecord.value());
            log.info("MKL Topic: " + consumerRecord.topic());
            log.info("MKL Partition: " + consumerRecord.partition());
            log.info("MKL Offset: " + consumerRecord.offset());
            log.info("MKL Timestamp: " + consumerRecord.timestamp());
            log.info("MKL Key: " + consumerRecord.key());
            acknowledgment.acknowledge();
        } catch (Exception e) {
            acknowledgment.nack(100);
        }
    }
}
