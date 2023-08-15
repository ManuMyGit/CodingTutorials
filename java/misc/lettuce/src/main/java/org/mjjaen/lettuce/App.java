package org.mjjaen.lettuce;

import io.lettuce.core.RedisClient;
import org.mjjaen.lettuce.asynchronous.RedisAsynchronous;
import org.mjjaen.lettuce.reactive.RedisReactive;
import org.mjjaen.lettuce.synchronous.RedisSynchronous;
import org.mjjaen.lettuce.transasctions.RedisTransactions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableAutoConfiguration
public class App implements CommandLineRunner {
    @Autowired
    private RedisSynchronous redisSynchronous;
    @Autowired
    private RedisAsynchronous redisAsynchronous;
    @Autowired
    private RedisReactive redisReactive;
    @Autowired
    private RedisTransactions redisTransactions;

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) {
        redisSynchronous.runRedisSyncExample();
        redisAsynchronous.runRedisAsyncExample();
        redisReactive.runRedisReactiveExample();
        redisTransactions.runRedisTransactinExample();
    }
}
