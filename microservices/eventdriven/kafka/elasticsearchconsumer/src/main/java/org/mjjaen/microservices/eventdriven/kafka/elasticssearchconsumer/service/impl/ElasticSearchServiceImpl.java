package org.mjjaen.microservices.eventdriven.kafka.elasticssearchconsumer.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.*;
import org.elasticsearch.common.xcontent.XContentType;
import org.mjjaen.microservices.eventdriven.kafka.elasticssearchconsumer.businessObject.KafkaMessage;
import org.mjjaen.microservices.eventdriven.kafka.elasticssearchconsumer.configuration.ElasticSearchConfiguration;
import org.mjjaen.microservices.eventdriven.kafka.elasticssearchconsumer.service.ElasticSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
@Log4j2
public class ElasticSearchServiceImpl implements ElasticSearchService {
    @Autowired
    private ElasticSearchConfiguration elasticSearchConfiguration;

    private static RestHighLevelClient client;

    @Override
    public RestHighLevelClient createClient() {
        if(client == null) {
            RestClientBuilder builder = RestClient.builder(new HttpHost(elasticSearchConfiguration.getHostname(), elasticSearchConfiguration.getPort(), elasticSearchConfiguration.getSchema()));if (elasticSearchConfiguration.getUsername() != null && !elasticSearchConfiguration.getUsername().isEmpty() && elasticSearchConfiguration.getPassword() != null && !elasticSearchConfiguration.getPassword().isEmpty()) {
                final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
                credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(elasticSearchConfiguration.getUsername(), elasticSearchConfiguration.getPassword()));
                builder.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    @Override
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpAsyncClientBuilder) {
                        return httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                    }
                });
            }
            client = new RestHighLevelClient(builder);
        }
        return client;
    }

    @Override
    public void insertData(RestHighLevelClient client, KafkaMessage kafkaMessage) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonKafkaMessage = mapper.writeValueAsString(kafkaMessage);
        //Id to make the twitter insertion idempotent. We're using the id of the tweet since this data is unique in twitter
        String id = String.valueOf(kafkaMessage.getId());

        IndexRequest indexRequest = new IndexRequest("tweets").id(id).source(jsonKafkaMessage, XContentType.JSON);

        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        log.info("Id: " + indexResponse.getId());
    }

    @Override
    public void insertDataBulk(RestHighLevelClient client, List<KafkaMessage> kafkaMessages) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        for(KafkaMessage kafkaMessage : kafkaMessages) {
            ObjectMapper mapper = new ObjectMapper();
            String jsonKafkaMessage = mapper.writeValueAsString(kafkaMessage);
            //Id to make the twitter insertion idempotent. We're using the id of the tweet since this data is unique in twitter
            String id = String.valueOf(kafkaMessage.getId());
            IndexRequest indexRequest = new IndexRequest("tweets").id(id).source(jsonKafkaMessage, XContentType.JSON);
            bulkRequest.add(indexRequest);
        }
        BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        Arrays.asList(bulkResponse.getItems()).stream().forEach(e -> log.info("Id bulked: " + e.getId()));
    }

    @PreDestroy
    public void closeElasticSearchClient() {
        log.info("Closing elastic search client ...");
        try {
            client.close();
            log.info("Client closed.");
        } catch (IOException e) {
            log.error("Error closing elastic search client", e);
        }
    }
}
