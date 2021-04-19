package org.mjjaen.microservices.eventdriven.kafka.twitterproducer.service;

import com.twitter.hbc.core.Client;

import java.util.concurrent.BlockingQueue;

public interface TwitterService {
    BlockingQueue<String> createMessageQueue(Integer capacity);
    Client createTwitterClient(BlockingQueue<String> messageQueue, String ... hotTopics);
    void connect(Client client);
    void stop(Client client);
}
