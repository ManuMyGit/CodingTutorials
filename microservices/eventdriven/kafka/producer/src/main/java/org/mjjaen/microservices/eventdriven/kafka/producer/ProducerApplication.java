package org.mjjaen.microservices.eventdriven.kafka.producer;

import org.mjjaen.microservices.eventdriven.kafka.producer.businessObject.MyMessage;
import org.mjjaen.microservices.eventdriven.kafka.producer.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.mjjaen.microservices.eventdriven.kafka"})
public class ProducerApplication //implements CommandLineRunner
{
    @Autowired
    private ProducerService producerService;

    public static void main( String[] args )
    {
        SpringApplication.run(ProducerApplication.class, args);
    }

    /*@Override
    public void run(String... args) throws Exception {
        MyMessage myMessage = new MyMessage();
        for(int i = 0; i < 5; i ++){
            myMessage.setMessage("Message " + i);
            producerService.sendMessage("my_topic", null, myMessage, false, true);
        }
    }*/
}
