package org.mjjaen.microservices.eventdriven.kafka.producer.service;

import org.mjjaen.microservices.eventdriven.kafka.producer.businessObject.MyMessage;

public interface ProducerService {
    boolean sendMessage(String topic, String key, MyMessage message, boolean close, boolean isKafkaTemplate);
}
