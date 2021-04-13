package org.mjjaen.microservices.eventdriven.kafka.producer.controller;

import org.mjjaen.microservices.eventdriven.kafka.producer.businessObject.MyMessage;
import org.mjjaen.microservices.eventdriven.kafka.producer.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {
    @Autowired
    private ProducerService producerService;

    @PostMapping("/publishkt")
    public String sendMessageKafkaTemplate(@RequestBody MyMessage message, @RequestParam(name="topic", required = true) String topic, @RequestParam(name="key", required = false) String key) {
        producerService.sendMessage(topic, key, message, false, true);
        return "Sent!";
    }

    @PostMapping("/publishkp")
    public String sendMessageKafkaProducer(@RequestBody MyMessage message, @RequestParam(name="topic", required = true) String topic, @RequestParam(name="key", required = false) String key) {
        producerService.sendMessage(topic, key, message, false, false);
        return "Sent!";
    }
}
