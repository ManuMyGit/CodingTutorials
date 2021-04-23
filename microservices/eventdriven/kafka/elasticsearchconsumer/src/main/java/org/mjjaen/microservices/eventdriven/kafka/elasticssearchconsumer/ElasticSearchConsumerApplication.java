package org.mjjaen.microservices.eventdriven.kafka.elasticssearchconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ElasticSearchConsumerApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(ElasticSearchConsumerApplication.class, args);
    }
}
