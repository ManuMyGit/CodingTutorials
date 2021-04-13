package org.mjjaen.microservices.eventdrivenrabbitmq.publisher.service.impl;

import lombok.extern.log4j.Log4j2;
import org.mjjaen.microservices.eventdrivenrabbitmq.rconfiguration.businessObject.MessageBody;
import org.mjjaen.microservices.eventdrivenrabbitmq.rconfiguration.rabbitconfig.DirectQueueConfiguration;
import org.mjjaen.microservices.eventdrivenrabbitmq.rconfiguration.rabbitconfig.FanoutQueueConfiguration;
import org.mjjaen.microservices.eventdrivenrabbitmq.rconfiguration.rabbitconfig.HeaderQueueConfiguration;
import org.mjjaen.microservices.eventdrivenrabbitmq.rconfiguration.rabbitconfig.TopicQueueConfiguration;
import org.mjjaen.microservices.eventdrivenrabbitmq.publisher.service.PublisherService;
import org.mjjaen.microservices.eventdrivenrabbitmq.publisher.service.SentType;
import org.mjjaen.microservices.eventdrivenrabbitmq.publisher.utils.Utils;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@Service("PublishService")
@Log4j2
public class PublishServiceImpl implements PublisherService {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Autowired
    private MessageConverter messageConverter;

    @Override
    public boolean sendMessage(MessageBody messageBody, SentType sentType, @Nullable MessageProperties defaultMessageProperties) {
        boolean sent = false;
        Message message = messageConverter.toMessage(messageBody, Utils.createMessageProperties(defaultMessageProperties));
        try {
            log.info("Sending message [id=/"+ message.getMessageProperties().getMessageId() + "]: " + message.getBody());
            if(sentType.equals(SentType.FANOUT))
                rabbitTemplate.convertAndSend(FanoutQueueConfiguration.FANOUT_EXCHANGE, "", message);
            else if(sentType.equals(SentType.TOPIC))
                rabbitTemplate.convertAndSend(TopicQueueConfiguration.TOPIC_EXCHANGE, TopicQueueConfiguration.TOPIC_ROUTING_KEY, message);
            else if(sentType.equals(SentType.DIRECT))
                rabbitTemplate.convertAndSend(DirectQueueConfiguration.QUEUE_EXCHANGE, DirectQueueConfiguration.QUEUE_ROUTING_KEY, message);
            else if(sentType.equals(SentType.HEADER))
                rabbitTemplate.convertAndSend(HeaderQueueConfiguration.HEADER_EXCHANGE, "", message);
            else
                throw new IllegalArgumentException("Illegal sent type");
            log.info("Message [id=" + message.getMessageProperties().getMessageId() +"] sent!");
            sent = true;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return sent;
    }
}
