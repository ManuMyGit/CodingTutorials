package org.mjjaen.springdata.redis;

import lombok.extern.slf4j.Slf4j;
import org.mjjaen.springdata.redis.examples.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableRedisRepositories
@Slf4j
public class RedisApplication implements CommandLineRunner {
    private static ApplicationContext applicationContext;

    @Autowired
    private GeospatialExamples geospatialExamples;
    @Autowired
    private HashExamples hashExamples;
    @Autowired
    private ListOperationsExamples listOperationsExamples;
    @Autowired
    private PubSubExamples pubSubExamples;
    @Autowired
    private SetOperationsExamples setOperationsExamples;
    @Autowired
    private ValueOperationsExamples valueOperationsExamples;
    @Autowired
    private ZSetOperationsExamples zSetOperationsExamples;

    public static void main( String[] args ) {
        applicationContext = SpringApplication.run(RedisApplication.class, args);
        String [] beans = applicationContext.getBeanDefinitionNames();
        System.out.println(beans);
        System.exit(0);
    }

    @Override
    public void run(String... args) {
        pubSubExamples.runExamplePublisher();
        geospatialExamples.runExamples();
        hashExamples.runExamples();
        listOperationsExamples.runExamples();
        setOperationsExamples.runExamples();
        valueOperationsExamples.runExamples();
        zSetOperationsExamples.runExamples();
    }
}
