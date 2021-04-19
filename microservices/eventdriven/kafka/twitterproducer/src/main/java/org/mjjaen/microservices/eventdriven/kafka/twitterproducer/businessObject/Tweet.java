package org.mjjaen.microservices.eventdriven.kafka.twitterproducer.businessObject;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.Data;

import java.sql.Timestamp;

@Data
@JsonIncludeProperties({"created_at", "id", "text", "user", "quote_count", "reply_count", "retweet_count", "favorite_count", "favorited", "retweeted", "timestamp_ms"})
public class Tweet {
    @JsonFormat(pattern="EEE MMM dd HH:mm:ss Z YYYY")
    private Timestamp created_at;
    private long id;
    private String text;
    private User user;
    private boolean is_quote_status;
    private int quote_count;
    private int reply_count;
    private int retweet_count;
    private int favorite_count;
    private boolean favorited;
    private boolean retweeted;
    private String timestamp_ms;
}
