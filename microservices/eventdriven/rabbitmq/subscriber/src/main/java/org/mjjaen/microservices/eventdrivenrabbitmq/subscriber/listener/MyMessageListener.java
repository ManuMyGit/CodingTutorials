package org.mjjaen.microservices.eventdrivenrabbitmq.subscriber.listener;

import com.rabbitmq.client.Channel;
import lombok.extern.log4j.Log4j2;
import org.mjjaen.microservices.eventdrivenrabbitmq.configuration.businessObject.MessageBody;
import org.mjjaen.microservices.eventdrivenrabbitmq.configuration.rabbitconfig.HeaderQueueConfiguration;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class MyMessageListener {
    @RabbitListener(queues = HeaderQueueConfiguration.PAYMENT_QUEUE_HEADER)
    public void listener(MessageBody message) {
        log.info("Message read: " + message);
    }

    @RabbitListener(queues = HeaderQueueConfiguration.FINANCE_QUEUE_HEADER)
    public void listener(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        log.info("Message read: " + message);
    }
}