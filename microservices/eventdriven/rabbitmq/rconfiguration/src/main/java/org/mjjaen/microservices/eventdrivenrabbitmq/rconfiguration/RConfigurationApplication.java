package org.mjjaen.microservices.eventdrivenrabbitmq.rconfiguration;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Log4j2
public class RConfigurationApplication implements CommandLineRunner
{
    public static void main( String[] args )
    {
        SpringApplication.run(RConfigurationApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //log.info("Creating RabbitMQ infrastructure ...");
        //amqpAdmin.initialize(); To force the admin to get executed
        log.info("RabbitMQ infrastructure created.");
    }
}
