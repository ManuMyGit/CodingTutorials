package org.mjjaen.lettuce.configuration;

import io.lettuce.core.ConnectionFuture;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LettuceConfiguration {
    @Autowired

    @Bean(destroyMethod = "shutdown")
    public RedisClient redisClient() {
        CharSequence cs = "Redis password"; //Only required if redis access is proctected
        return RedisClient.create(RedisURI.Builder.redis("localhost", 6379).withSsl(false).withDatabase(0).withPassword(cs).build());
    }

    @Bean(destroyMethod = "close")
    StatefulRedisConnection<String, String> connection(RedisClient redisClient) {
        return redisClient.connect();
    }
}
