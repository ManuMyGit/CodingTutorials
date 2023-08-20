package org.mjjaen.springcache.config;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.*;

import java.time.Duration;

@Configuration
public class RedisConfig {
    @Value("${redis.password}")
    private String password;

    @Value("${redis.database}")
    private Integer database;

    @Value("${redis.port}")
    private Integer port;

    @Value("${redis.host}")
    private String host;

    @Value("${redis.socketTimeout}")
    private Integer socketTimeout;

    @Value("${redis.commandTimeout}")
    private Integer commandTimeout;

    @Value("${redis.ttl}")
    private Integer ttl;

    @Bean
    public RedisCacheConfiguration cacheConfiguration() {
        RedisSerializationContext.SerializationPair<String> keySerializer = RedisSerializationContext
                .SerializationPair.fromSerializer(RedisSerializer.string());
        RedisSerializationContext.SerializationPair<?> valueSerializer = RedisSerializationContext
                .SerializationPair.fromSerializer(RedisSerializer.json());

        return RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.ofMillis(ttl))
                        //.prefixCacheNameWith("paymentsCache")
                        .serializeKeysWith(keySerializer)
                        .serializeValuesWith(valueSerializer);
    }

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        //Client config
        final SocketOptions socketOptions = SocketOptions.builder().connectTimeout(Duration.ofMillis(socketTimeout)).build();
        final ClientOptions clientOptions = ClientOptions.builder()
                .autoReconnect(true)
                .socketOptions(socketOptions)
                .build();
        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .commandTimeout(Duration.ofMillis(commandTimeout))
                .clientOptions(clientOptions)
                .build();

        //Server config
        RedisStandaloneConfiguration serverConfig = new RedisStandaloneConfiguration();
        serverConfig.setPassword(RedisPassword.of(password));
        serverConfig.setDatabase(database);
        serverConfig.setPort(port);
        serverConfig.setHostName(host);

        //Connection Factory
        final LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(serverConfig, clientConfig);
        lettuceConnectionFactory.setValidateConnection(true);
        return new LettuceConnectionFactory(serverConfig, clientConfig);
    }
}
