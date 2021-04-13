package org.mjjaen.microservices.eventdriven.kafka.consumer;

import org.mjjaen.microservices.eventdriven.kafka.consumer.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.concurrent.CountDownLatch;

@SpringBootApplication
@ComponentScan(basePackages = {"org.mjjaen.microservices.eventdriven.kafka"})
public class ConsumerApplication implements CommandLineRunner {
    @Autowired
    private ConsumerService consumerService;

    public static void main( String[] args )
    {
        SpringApplication.run(ConsumerApplication.class);
    }

    @Override
    public void run(String... args) throws Exception {
        consumerService.subscribeToTopics("my_topic");
        consumerService.consumeMessages();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            consumerService.shutDown();
        }));
    }
}
