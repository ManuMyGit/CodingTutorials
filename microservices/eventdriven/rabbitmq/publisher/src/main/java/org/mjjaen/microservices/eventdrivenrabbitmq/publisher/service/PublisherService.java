package org.mjjaen.microservices.eventdrivenrabbitmq.publisher.service;

import org.mjjaen.microservices.eventdrivenrabbitmq.configuration.businessObject.MessageBody;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.lang.Nullable;

public interface PublisherService {
    boolean sendMessage(MessageBody message, SentType sentType, @Nullable MessageProperties messageProperties);
}
