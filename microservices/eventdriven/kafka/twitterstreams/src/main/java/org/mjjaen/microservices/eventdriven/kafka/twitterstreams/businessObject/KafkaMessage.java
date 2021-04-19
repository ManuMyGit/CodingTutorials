package org.mjjaen.microservices.eventdriven.kafka.twitterstreams.businessObject;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class KafkaMessage implements Serializable {
    private long id;
    private Timestamp createdAt;
    private String text;
    private int quote_count;
    private int reply_count;
    private int retweet_count;
    private int favorite_count;
    private boolean favorited;
    private boolean retweeted;
    private String userName;
}
