package org.mjjaen.microservices.eventdriven.kafka.consumer.service;

import java.util.concurrent.CountDownLatch;

public interface ConsumerService {
    void subscribeToTopics(String ... topics);
    void consumeMessages();
    void shutDown();
}
