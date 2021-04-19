package org.mjjaen.microservices.eventdriven.kafka.elasticssearchconsumer.consumer;

import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.elasticsearch.client.RestHighLevelClient;
import org.mjjaen.microservices.eventdriven.kafka.elasticssearchconsumer.businessObject.KafkaMessage;
import org.mjjaen.microservices.eventdriven.kafka.elasticssearchconsumer.service.ElasticSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.listener.BatchAcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Log4j2
public class MyKafkaConsumerBatch implements BatchAcknowledgingMessageListener<String, KafkaMessage>  {
    @Autowired
    private ElasticSearchService elasticSearchService;

    @Override
    public void onMessage(List<ConsumerRecord<String, KafkaMessage>> list, Acknowledgment acknowledgment) {
        int listSize = list.size();
        log.info("Received " + listSize + " records.");
        boolean commit = true;
        try {
            if(listSize > 0) {
                List<KafkaMessage> kafkaMessages = list.stream().map(consumerRecord -> consumerRecord.value()).collect(Collectors.toList());
                RestHighLevelClient client = elasticSearchService.createClient();
                elasticSearchService.insertDataBulk(client, kafkaMessages);
                acknowledgment.acknowledge();
                log.info("Committed " + listSize + " offsets.");
            }
        } catch (IOException e) {
            acknowledgment.nack(100);
            log.error("Not committed " + listSize + " offsets.", e);
        }
    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
