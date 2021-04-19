package org.mjjaen.microservices.eventdriven.kafka.elasticssearchconsumer.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class MyKafkaConsumerFactory {
    public static final String ONE_BY_ONE = "one_by_one";
    public static final String BATCH = "batch";

    @Autowired
    private MyKafkaConsumer myKafkaConsumer;
    @Autowired
    private MyKafkaConsumerBatch myKafkaConsumerBatch;

    public Object createKafkaConsumer(String consumerType) {
        if(consumerType.equals(ONE_BY_ONE))
            return myKafkaConsumer;
        else if(consumerType.equals(BATCH))
            return myKafkaConsumerBatch;
        else
            throw new IllegalArgumentException("Invalid kafka consumer type");
    }
}
