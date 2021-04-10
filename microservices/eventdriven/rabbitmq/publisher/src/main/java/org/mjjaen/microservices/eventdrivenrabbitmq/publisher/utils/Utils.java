package org.mjjaen.microservices.eventdrivenrabbitmq.publisher.utils;

import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;

import java.util.Date;
import java.util.UUID;

public class Utils {
    public static MessageProperties createMessageProperties(MessageProperties defaultMessageProperties) {
        MessageProperties messageProperties = MessagePropertiesBuilder.fromClonedProperties(defaultMessageProperties)
                .setTimestampIfAbsent(new Date())
                .setContentEncodingIfAbsent("UTF-8")
                .setDeliveryModeIfAbsentOrDefault(MessageDeliveryMode.PERSISTENT)
                .setPriorityIfAbsentOrDefault(1)
                .setMessageIdIfAbsent(UUID.randomUUID().toString())
                .build();
        return messageProperties;
    }
}
