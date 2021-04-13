package org.mjjaen.microservices.eventdrivenrabbitmq.rconfiguration.rabbitconfig;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
        @PropertySource("classpath:application.properties")
})
public class DirectQueueConfiguration {
    public static final String QUEUE_NAME = "direct_queue";
    public static final String QUEUE_EXCHANGE = "direct_exchange";
    public static final String QUEUE_ROUTING_KEY = "direct.rk";

    public static final String DEAD_QUEUE_EXCHANGE = "direct_exchange_dead";
    public static final String DEAD_QUEUE_ROUTING_KEY = "direct.rk.dead";

    @Value("${direct.queue}")
    private String queueName;

    @Value("${direct.queue.exchange}")
    private String queueExchange;

    @Value("${direct.queue.routingKey}")
    private String queueRoutingKey;

    @Bean
    public Queue directQueue() {
        return QueueBuilder.nonDurable(queueName)
                .deadLetterExchange(queueExchange.concat("_retry"))
                .deadLetterRoutingKey(queueRoutingKey.concat(".retry"))
                .ttl(1209600000)
                .maxLength(100)
                .build();
    }

    @Bean
    public Queue retryDirectQueue() {
        return QueueBuilder.nonDurable(queueName.concat("_retry"))
                .deadLetterExchange(queueExchange)
                .deadLetterRoutingKey(queueRoutingKey)
                .ttl(2000)
                .maxLength(100)
                .build();
    }

    @Bean
    public Queue deadDirectQueue() {
        return QueueBuilder.nonDurable(queueName.concat("_dead"))
                .ttl(60000)
                .maxLength(100)
                .build();
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(queueExchange, false, false);
    }

    @Bean
    public Binding directBinding(Queue directQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(directQueue).to(directExchange).with(queueRoutingKey);
    }

    @Bean
    public DirectExchange retryDirectExchange() {
        return new DirectExchange(queueExchange.concat("_retry"), false, false);
    }

    @Bean
    public Binding retryDirectBinding(Queue retryDirectQueue, DirectExchange retryDirectExchange) {
        return BindingBuilder.bind(retryDirectQueue).to(retryDirectExchange).with(queueRoutingKey.concat(".retry"));
    }

    @Bean
    public DirectExchange deadDirectExchange() {
        return new DirectExchange(queueExchange.concat("_dead"), false, false);
    }

    @Bean
    public Binding deadDirectBinding(Queue deadDirectQueue, DirectExchange deadDirectExchange) {
        return BindingBuilder.bind(deadDirectQueue).to(deadDirectExchange).with(queueRoutingKey.concat(".dead"));
    }
}
