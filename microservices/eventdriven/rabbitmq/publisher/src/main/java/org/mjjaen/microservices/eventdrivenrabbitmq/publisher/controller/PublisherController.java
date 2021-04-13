package org.mjjaen.microservices.eventdrivenrabbitmq.publisher.controller;

import org.mjjaen.microservices.eventdrivenrabbitmq.publisher.service.PublisherService;
import org.mjjaen.microservices.eventdrivenrabbitmq.publisher.service.SentType;
import org.mjjaen.microservices.eventdrivenrabbitmq.rconfiguration.businessObject.MessageBody;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublisherController {
    @Autowired
    private PublisherService publisherService;

    @PostMapping("/publishFanout")
    public String sendMessageFanout(@RequestBody MessageBody message) {
        if(publisherService.sendMessage(message, SentType.FANOUT, MessagePropertiesBuilder.newInstance().build()))
            return "Sent!";
        else
            return "Not send!";
    }

    @PostMapping("/publishTopic")
    public String sendMessageTopic(@RequestBody MessageBody message) {
        if(publisherService.sendMessage(message, SentType.TOPIC, MessagePropertiesBuilder.newInstance().build()))
            return "Sent!";
        else
            return "Not send!";
    }

    @PostMapping("/publishDirect")
    public String sendMessageDirect(@RequestBody MessageBody message) {
        if(publisherService.sendMessage(message, SentType.DIRECT, MessagePropertiesBuilder.newInstance().build()))
            return "Sent!";
        else
            return "Not send!";
    }

    @PostMapping("/publishHeader")
    public String sendMessageHeader(@RequestBody MessageBody message, @RequestParam(name="department", required = true) String department) {
        MessageProperties messageProperties = MessagePropertiesBuilder.newInstance().build();
        messageProperties.setHeader("department", department);
        if(publisherService.sendMessage(message, SentType.HEADER, messageProperties))
            return "Sent!";
        else
            return "Not send!";
    }
}