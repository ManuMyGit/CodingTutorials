package org.mjjaen.microservices.eventdriven.kafka.consumer.configuration;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.mjjaen.microservices.eventdriven.kafka.consumer.businessObject.MyMessage;
import org.mjjaen.microservices.eventdriven.kafka.consumer.consumers.MyKafkaConsumer;
import org.mjjaen.microservices.eventdriven.kafka.consumer.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Properties;

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

    @Autowired
    private MyKafkaConsumer myKafkaConsumer;

    @Bean
    public ConsumerFactory<String, MyMessage> consumerFactory() {
        //This is needed for Kafka to Trust MyMessage class to deserialize it
        JsonDeserializer<MyMessage> deserializer = new JsonDeserializer<>(MyMessage.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);

        return new DefaultKafkaConsumerFactory<String, MyMessage>(Utils.convertPropertiesToMap(consumerProperties()), new StringDeserializer(), deserializer);
    }

    @Bean
    public Properties consumerProperties() {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroup);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return properties;
    }

    //To enable the manual listener
    @Bean
    public Consumer kafkaConsumer() {
        KafkaConsumer<String, String> consumer = new KafkaConsumer(Utils.convertPropertiesToMap(consumerProperties()));
        return consumer;
    }

    //This bean is used for @KafkaListener
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, MyMessage>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, MyMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(3);
        factory.getContainerProperties().setPollTimeout(3000);
        return factory;
    }

    //To enable the MyKafkaListener listener
    @Bean
    public MessageListenerContainer messageListenerContainer() {
        ContainerProperties containerProps = new ContainerProperties(topic);
        containerProps.setMessageListener(myKafkaConsumer);
        containerProps.setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        KafkaMessageListenerContainer<String, MyMessage> container = new KafkaMessageListenerContainer<String, MyMessage>(consumerFactory(), containerProps);
        return container;
    }
}
