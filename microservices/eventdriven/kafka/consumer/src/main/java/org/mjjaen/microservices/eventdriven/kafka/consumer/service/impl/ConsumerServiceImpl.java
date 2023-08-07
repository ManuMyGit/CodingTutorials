package org.mjjaen.microservices.eventdriven.kafka.consumer.service.impl;

import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.errors.WakeupException;
import org.mjjaen.microservices.eventdriven.kafka.consumer.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Log4j2
public class ConsumerServiceImpl implements ConsumerService {
    private final ExecutorService executorService = Executors.newFixedThreadPool(1);

    @Autowired
    private Consumer<String, String> kafkaConsumer;

    public void subscribeToTopics(String... topics) {
        kafkaConsumer.subscribe(Arrays.asList(topics));
    }

    public void consumeMessages() {
        executorService.submit(() -> {
            try {
                while (true) {
                    ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(100));
                    for (ConsumerRecord<String, String> record : records) {
                        log.info("Data read ...");
                        log.info("Data: " + record.value());
                        log.info("Topic: " + record.topic());
                        log.info("Partition: " + record.partition());
                        log.info("Offset: " + record.offset());
                        log.info("Timestamp: " + record.timestamp());
                        log.info("Key: " + record.key());
                    }
                }
            } catch (WakeupException e) {
                log.error("Receive shutdown signal", e);
            } finally {
                kafkaConsumer.close();
            }
        });
    }

    public void shutDown() {
        kafkaConsumer.wakeup();
        executorService.shutdown();
    }
}
