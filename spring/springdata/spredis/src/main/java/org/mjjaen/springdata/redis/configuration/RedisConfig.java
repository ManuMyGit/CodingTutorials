package org.mjjaen.springdata.redis.configuration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import org.mjjaen.springdata.redis.businessObject.Employee;
import org.mjjaen.springdata.redis.pubsub.RedisListener;
import org.mjjaen.springdata.redis.pubsub.model.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@EnableRedisRepositories
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

    @Bean
    public RedisTemplate<String, Object> employeeRedisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setDefaultSerializer(new StringRedisSerializer());
        template.setConnectionFactory(redisConnectionFactory());
        template.setHashValueSerializer(new Jackson2JsonRedisSerializer(Employee.class));
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public RedisTemplate<String, Object> customerRedisTemplate() {
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        RedisTemplate template = new RedisTemplate();
        template.setDefaultSerializer(new StringRedisSerializer());
        template.setConnectionFactory(redisConnectionFactory());
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();

        return template;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate() {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(redisConnectionFactory());
        stringRedisTemplate.setEnableTransactionSupport(true);
        return stringRedisTemplate;
    }

    //From here to the bottom, configuration required for the Pub/Sub to work.
    @Bean
    public RedisTemplate<String, Object> pubSubTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        template.setDefaultSerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer(Order.class));
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public ChannelTopic channelTopic() {
        return new ChannelTopic("anyTopic");
    }

    @Bean
    public MessageListenerAdapter messageListener() {
        return new MessageListenerAdapter(new RedisListener(), "onMessage");
    }

    @Bean
    public RedisMessageListenerContainer redisContainer(ChannelTopic topic) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory());
        container.addMessageListener(messageListener(), channelTopic());
        return container;
    }
}