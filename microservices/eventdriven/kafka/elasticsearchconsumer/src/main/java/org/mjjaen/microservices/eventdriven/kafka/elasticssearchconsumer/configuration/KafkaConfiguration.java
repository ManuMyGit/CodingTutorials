package org.mjjaen.microservices.eventdriven.kafka.elasticssearchconsumer.configuration;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.mjjaen.microservices.eventdriven.kafka.elasticssearchconsumer.businessObject.KafkaMessage;
import org.mjjaen.microservices.eventdriven.kafka.elasticssearchconsumer.consumer.MyKafkaConsumer;
import org.mjjaen.microservices.eventdriven.kafka.elasticssearchconsumer.consumer.MyKafkaConsumerBatch;
import org.mjjaen.microservices.eventdriven.kafka.elasticssearchconsumer.consumer.MyKafkaConsumerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySources({
        @PropertySource("classpath:application.properties")
})
@EnableKafka
public class KafkaConfiguration {
    @Value("${consumer.group}")
    private String consumerGroup;

    @Value("${topic.name}")
    private String topic;

    @Value("${concurrent.consumers}")
    private int concurrentConsumers;

    @Value("${consumer.strategy.type}")
    private String consumerStrategyType;

    @Autowired
    private MyKafkaConsumerFactory myKafkaConsumerFactory;

    @Bean
    public ConsumerFactory<String, KafkaMessage> consumerFactory() {
        //This is needed for Kafka to Trust MyMessage class to deserialize it
        JsonDeserializer<KafkaMessage> deserializer = new JsonDeserializer<>(KafkaMessage.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);

        return new DefaultKafkaConsumerFactory<String, KafkaMessage>(consumerProperties(), new StringDeserializer(), deserializer);
    }

    @Bean
    public Map<String, Object> consumerProperties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroup);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "10");
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        return properties;
    }

    //To enable the MyKafkaListener listener
    @Bean
    public MessageListenerContainer messageListenerContainer() {
        ContainerProperties containerProps = new ContainerProperties(topic);
        containerProps.setMessageListener(myKafkaConsumerFactory.createKafkaConsumer(consumerStrategyType));
        containerProps.setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(concurrentConsumers);
        executor.setMaxPoolSize(concurrentConsumers);
        executor.setQueueCapacity(concurrentConsumers * 333);
        executor.setThreadNamePrefix("manu-exec-");
        executor.initialize();
        containerProps.setConsumerTaskExecutor(executor);
        ConcurrentMessageListenerContainer<String, KafkaMessage> container = new ConcurrentMessageListenerContainer<String, KafkaMessage>(consumerFactory(), containerProps);
        container.setConcurrency(concurrentConsumers);
        return container;
    }
}
