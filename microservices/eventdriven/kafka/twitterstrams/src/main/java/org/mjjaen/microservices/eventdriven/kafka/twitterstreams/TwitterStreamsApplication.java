package org.mjjaen.microservices.eventdriven.kafka.twitterstreams;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TwitterStreamsApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(TwitterStreamsApplication.class, args);
    }
}
