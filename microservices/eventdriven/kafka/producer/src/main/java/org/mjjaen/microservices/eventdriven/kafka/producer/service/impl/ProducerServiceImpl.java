package org.mjjaen.microservices.eventdriven.kafka.producer.service.impl;

import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.mjjaen.microservices.eventdriven.kafka.producer.businessObject.MyMessage;
import org.mjjaen.microservices.eventdriven.kafka.producer.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.Future;

@Service
@Log4j2
public class ProducerServiceImpl implements ProducerService {
    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private KafkaTemplate<String, MyMessage> kafkaTemplate;

    @Override
    public boolean sendMessage(String topic, String key, MyMessage message, boolean close, boolean isKafkaTemplate) {
        if (!isKafkaTemplate) {
            ProducerRecord<String, String> producerRecord;
            if(key == null)
                producerRecord = new ProducerRecord(topic, message);
            else
                producerRecord = new ProducerRecord(topic, key, message);
            Future<RecordMetadata> recordMetadata = kafkaProducer.send(producerRecord, new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (e == null) {
                        log.info("Message sent. Message information");
                        log.info("Topic: " + recordMetadata.topic());
                        log.info("Partition: " + recordMetadata.partition());
                        log.info("Offset: " + recordMetadata.offset());
                        log.info("Key size: " + recordMetadata.serializedKeySize());
                        log.info("Key Value: " + recordMetadata.serializedValueSize());
                    } else {
                        log.error("Error while writting data: ", e);
                    }
                }
            });
            kafkaProducer.flush();
            if (close)
                kafkaProducer.close();
        } else {
            ListenableFuture<SendResult<String, MyMessage>> future;
            if(key == null)
                future = kafkaTemplate.send(topic, message);
            else
                future = kafkaTemplate.send(topic, key, message);
            future.addCallback(new ListenableFutureCallback<SendResult<String, MyMessage>>() {
                @Override
                public void onFailure(Throwable throwable) {
                    log.error("Error while writting data: ", throwable);
                }

                @Override
                public void onSuccess(SendResult<String, MyMessage> stringStringSendResult) {
                    log.info("LF Message sent. Message information");
                    log.info("LF Topic: " + stringStringSendResult.getRecordMetadata().topic());
                    log.info("LF Partition: " + stringStringSendResult.getRecordMetadata().partition());
                    log.info("LF Offset: " + stringStringSendResult.getRecordMetadata().offset());
                    log.info("LF Key size: " + stringStringSendResult.getRecordMetadata().serializedKeySize());
                    log.info("LF Key Value: " + stringStringSendResult.getRecordMetadata().serializedValueSize());
                }
            });
            kafkaTemplate.flush();
        }
        return true;
    }
}
