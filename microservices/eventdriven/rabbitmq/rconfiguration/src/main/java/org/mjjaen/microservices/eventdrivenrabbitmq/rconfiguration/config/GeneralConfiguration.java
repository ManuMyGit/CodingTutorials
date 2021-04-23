package org.mjjaen.microservices.eventdrivenrabbitmq.rconfiguration.config;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.PooledChannelConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@Configuration
@PropertySources({
        @PropertySource("classpath:application.properties")
})
public class GeneralConfiguration {
    @Value("${rabbit.host}")
    private String host;

    @Value("${rabbit.port}")
    private String port;

    @Value("${rabbit.connection.timeout}")
    private String timeout;

    @Value("${rabbit.username}")
    private String username;

    @Value("${rabbit.password}")
    private String password;

    @Bean
    public MessageConverter messageConverter() {
        Jackson2JsonMessageConverter jsonMessageConverter = new Jackson2JsonMessageConverter();
        return jsonMessageConverter;
    }

    @Bean
    public PooledChannelConnectionFactory myConnectionFactory() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        if(host == null || host != null && host.isEmpty())
            host = "localhost";
        connectionFactory.setHost(host);
        int myPort = port != null && !port.isEmpty() ? Integer.valueOf(port) : ConnectionFactory.DEFAULT_AMQP_PORT;
        connectionFactory.setPort(myPort);
        int myTimeout = timeout != null && !timeout.isEmpty() ? Integer.valueOf(timeout) : ConnectionFactory.DEFAULT_CONNECTION_TIMEOUT;
        connectionFactory.setConnectionTimeout(myTimeout);
        if(username == null || username != null && username.isEmpty())
            username = "guest";
        connectionFactory.setUsername(username);
        if(password == null || password != null && password.isEmpty())
            password = "guest";
        connectionFactory.setPassword(password);
        connectionFactory.setAutomaticRecoveryEnabled(true);
        return new PooledChannelConnectionFactory(connectionFactory);
    }

    @Bean
    public AmqpTemplate template() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(myConnectionFactory());
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}
