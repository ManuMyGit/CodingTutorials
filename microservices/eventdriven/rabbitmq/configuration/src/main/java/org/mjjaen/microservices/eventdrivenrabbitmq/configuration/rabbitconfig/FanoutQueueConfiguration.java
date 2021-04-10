package org.mjjaen.microservices.eventdrivenrabbitmq.configuration.rabbitconfig;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutQueueConfiguration {
    public static final String PAYMENT_QUEUE = "payment_queue";
    public static final String FINANCE_QUEUE = "finance_queue";
    public static final String DELIVERY_QUEUE = "delivery_queue";
    public static final String FANOUT_EXCHANGE = "test_fanout_exchange";

    @Bean
    public Queue paymentQueue() {
        return QueueBuilder.nonDurable(PAYMENT_QUEUE)
                .ttl(600000)
                .maxLength(100)
                .build();
    }

    @Bean
    public Queue financeQueue() {
        return QueueBuilder.nonDurable(FINANCE_QUEUE)
                .ttl(600000)
                .maxLength(100)
                .build();
    }

    @Bean
    public Queue deliveryQueue() {
        return QueueBuilder.nonDurable(DELIVERY_QUEUE)
                .ttl(600000)
                .maxLength(100)
                .build();
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return ExchangeBuilder.fanoutExchange(FANOUT_EXCHANGE).durable(false).build();
    }

    @Bean
    public Binding paymentBinding(Queue paymentQueue, FanoutExchange exchange) {
        return BindingBuilder.bind(paymentQueue).to(exchange);
    }

    @Bean
    public Binding financeBinding(Queue financeQueue, FanoutExchange exchange) {
        return BindingBuilder.bind(financeQueue).to(exchange);
    }

    @Bean
    public Binding deliveryBinding(Queue deliveryQueue, FanoutExchange exchange) {
        return BindingBuilder.bind(deliveryQueue).to(exchange);
    }
}
