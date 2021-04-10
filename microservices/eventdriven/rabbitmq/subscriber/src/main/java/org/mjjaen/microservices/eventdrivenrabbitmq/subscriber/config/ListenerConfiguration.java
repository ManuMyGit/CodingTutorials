package org.mjjaen.microservices.eventdrivenrabbitmq.subscriber.config;

import org.mjjaen.microservices.eventdrivenrabbitmq.subscriber.listener.MyListener;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ListenerConfiguration {
    @Autowired
    private MyListener myListener;

    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private Queue deliveryQueueHeader;

    @Bean
    public SimpleMessageListenerContainer messageContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueues(deliveryQueueHeader);
        container.setExposeListenerChannel(true);
        container.setMaxConcurrentConsumers(1);
        container.setConcurrentConsumers(1);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setMessageListener(myListener);
        return container;
    }
}
