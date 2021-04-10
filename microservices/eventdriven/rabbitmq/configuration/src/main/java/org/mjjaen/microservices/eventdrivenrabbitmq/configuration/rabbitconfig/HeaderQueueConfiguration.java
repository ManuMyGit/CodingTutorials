package org.mjjaen.microservices.eventdrivenrabbitmq.configuration.rabbitconfig;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HeaderQueueConfiguration {
    public static final String PAYMENT_QUEUE_HEADER = "payment_queue_header";
    public static final String FINANCE_QUEUE_HEADER = "finance_queue_header";
    public static final String DELIVERY_QUEUE_HEADER = "delivery_queue_header";
    public static final String HEADER_EXCHANGE = "test_header_exchange";

    @Bean
    public Queue paymentQueueHeader() {
        return QueueBuilder.nonDurable(PAYMENT_QUEUE_HEADER)
                .ttl(600000)
                .maxLength(100)
                .build();
    }

    @Bean
    public Queue financeQueueHeader() {
        return QueueBuilder.nonDurable(FINANCE_QUEUE_HEADER)
                .ttl(600000)
                .maxLength(100)
                .build();
    }

    @Bean
    public Queue deliveryQueueHeader() {
        return QueueBuilder.nonDurable(DELIVERY_QUEUE_HEADER)
                .ttl(600000)
                .maxLength(100)
                .build();
    }

    @Bean
    public HeadersExchange headersExchange() {
        return ExchangeBuilder.headersExchange(HEADER_EXCHANGE)
                .durable(false)
                .build();
    }

    @Bean
    public Binding paymentHeaderBinding(Queue paymentQueueHeader, HeadersExchange exchange) {
        return BindingBuilder.bind(paymentQueueHeader).to(exchange).where("department").matches("payment");
    }

    @Bean
    public Binding financeHeaderBinding(Queue financeQueueHeader, HeadersExchange exchange) {
        return BindingBuilder.bind(financeQueueHeader).to(exchange).where("department").matches("finance");
    }

    @Bean
    public Binding deliveryHeaderBinding(Queue deliveryQueueHeader, HeadersExchange exchange) {
        return BindingBuilder.bind(deliveryQueueHeader).to(exchange).where("department").matches("delivery");
    }
}