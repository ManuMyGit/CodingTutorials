package org.mjjaen.microservices.eventdriven.kafka.twitterproducer.service;

import org.mjjaen.microservices.eventdriven.kafka.twitterproducer.businessObject.KafkaMessage;

public interface KafkaService {
    void sendMessage(String topic, String key, KafkaMessage message);
}
