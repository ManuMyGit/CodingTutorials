package org.mjjaen.microservices.eventdrivenrabbitmq.rconfiguration.rabbitconfig;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicQueueConfiguration {
    public static final String VENDOR_1_QUEUE = "first_vendor_queue";
    public static final String VENDOR_2_QUEUE = "second_vendor_queue";
    public static final String VENDOR_3_QUEUE = "third_vendor_queue";
    public static final String TOPIC_EXCHANGE = "test_topic_exchange";
    public static final String TOPIC_ROUTING_KEY = "test.topic.rk";

    @Bean
    public Queue firstVendorQueue() {
        return QueueBuilder.nonDurable(VENDOR_1_QUEUE)
                .ttl(600000)
                .maxLength(100)
                .build();
    }

    @Bean
    public Queue secondVendorQueue() {
        return QueueBuilder.nonDurable(VENDOR_2_QUEUE)
                .ttl(600000)
                .maxLength(100)
                .build();
    }

    @Bean
    public Queue thirdVendorQueue() {
        return QueueBuilder.nonDurable(VENDOR_3_QUEUE)
                .ttl(600000)
                .maxLength(100)
                .build();
    }

    @Bean
    public TopicExchange topicExchange() {
        return ExchangeBuilder.topicExchange(TOPIC_EXCHANGE)
                .durable(false)
                .build();
    }

    @Bean
    public Binding firstVendorBinding(Queue firstVendorQueue, TopicExchange exchange) {
        return BindingBuilder.bind(firstVendorQueue).to(exchange).with("test.topic");
    }

    @Bean
    public Binding secondVendorBinding(Queue secondVendorQueue, TopicExchange exchange) {
        return BindingBuilder.bind(secondVendorQueue).to(exchange).with("test.topic.#");
    }

    @Bean
    public Binding thirdVendorBinding(Queue thirdVendorQueue, TopicExchange exchange) {
        return BindingBuilder.bind(thirdVendorQueue).to(exchange).with("test.topic.#");
    }
}