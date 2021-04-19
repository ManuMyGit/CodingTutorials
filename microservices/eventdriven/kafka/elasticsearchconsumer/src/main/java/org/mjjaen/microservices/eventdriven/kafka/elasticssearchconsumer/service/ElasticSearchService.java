package org.mjjaen.microservices.eventdriven.kafka.elasticssearchconsumer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.elasticsearch.client.RestHighLevelClient;
import org.mjjaen.microservices.eventdriven.kafka.elasticssearchconsumer.businessObject.KafkaMessage;

import java.io.IOException;
import java.util.List;

public interface ElasticSearchService {
    RestHighLevelClient createClient();
    void insertData(RestHighLevelClient client, KafkaMessage kafkaMessage) throws IOException;
    void insertDataBulk(RestHighLevelClient client, List<KafkaMessage> kafkaMessages) throws IOException;
}
