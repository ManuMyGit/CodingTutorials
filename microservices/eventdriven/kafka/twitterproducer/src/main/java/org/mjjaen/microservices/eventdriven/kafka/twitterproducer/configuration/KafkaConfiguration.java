package org.mjjaen.microservices.eventdriven.kafka.twitterproducer.configuration;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.mjjaen.microservices.eventdriven.kafka.twitterproducer.businessObject.KafkaMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfiguration {
    @Bean
    public ProducerFactory<String, KafkaMessage> producerFactory() {
        return new DefaultKafkaProducerFactory<String, KafkaMessage>(producerProperties());
    }

    @Bean
    public Map<String, Object> producerProperties() {
        Map<String, Object> properties = new HashMap();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
        //5 requests in parallel
        properties.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, "5");
        //Max number of retries
        properties.put(ProducerConfig.RETRIES_CONFIG, Integer.toString(Integer.MAX_VALUE));
        properties.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, 180000);
        //Most secure and slow ack strategy
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        //Idempotence enabled
        properties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
        //Compression enabled
        properties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");
        //Batch size
        properties.put(ProducerConfig.LINGER_MS_CONFIG, "50");
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, Integer.toString(32*1024));
        return properties;
    }

    @Bean
    public KafkaTemplate<String, KafkaMessage> kafkaTemplate() {
        return new KafkaTemplate<String, KafkaMessage>(producerFactory());
    }
}
