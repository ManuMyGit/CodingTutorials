package org.mjjaen.microservices.eventdriven.kafka.elasticssearchconsumer.consumer;

import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.elasticsearch.client.RestHighLevelClient;
import org.mjjaen.microservices.eventdriven.kafka.elasticssearchconsumer.businessObject.KafkaMessage;
import org.mjjaen.microservices.eventdriven.kafka.elasticssearchconsumer.service.ElasticSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Log4j2
public class MyKafkaConsumer implements AcknowledgingMessageListener<String, KafkaMessage> {
    @Autowired
    private ElasticSearchService elasticSearchService;

    @Override
    public void onMessage(ConsumerRecord<String, KafkaMessage> consumerRecord, Acknowledgment acknowledgment) {
        try {
            KafkaMessage kafkaMessage = consumerRecord.value();
            RestHighLevelClient client = elasticSearchService.createClient();
            elasticSearchService.insertData(client, kafkaMessage);
            acknowledgment.acknowledge(); //This makes the delivery semantic at least once
        } catch (IOException e) {
            acknowledgment.nack(100);
        }
    }
}
