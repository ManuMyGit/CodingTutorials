package org.mjjaen.microservices.eventdrivenrabbitmq.publisher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.mjjaen.microservices.eventdrivenrabbitmq"})
public class PublisherApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(PublisherApplication.class, args);
    }
}
