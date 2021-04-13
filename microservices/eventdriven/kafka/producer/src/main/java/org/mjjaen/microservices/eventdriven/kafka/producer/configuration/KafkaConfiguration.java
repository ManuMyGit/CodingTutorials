package org.mjjaen.microservices.eventdriven.kafka.producer.configuration;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.mjjaen.microservices.eventdriven.kafka.producer.businessObject.MyMessage;
import org.mjjaen.microservices.eventdriven.kafka.producer.utils.Utils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Properties;

@Configuration
public class KafkaConfiguration {
    @Bean
    public ProducerFactory<String, MyMessage> producerFactory() {
        return new DefaultKafkaProducerFactory<String, MyMessage>(Utils.convertPropertiesToMap(producerProperties()));
    }

    @Bean
    public Properties producerProperties() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
        return properties;
    }

    @Bean
    public KafkaTemplate<String, MyMessage> kafkaTemplate() {
        return new KafkaTemplate<String, MyMessage>(producerFactory());
    }

    @Bean
    public KafkaProducer<String, MyMessage> kafkaProducer() {
        KafkaProducer<String, MyMessage> producer = new KafkaProducer(producerProperties());
        return producer;
    }
}
