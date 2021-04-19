package org.mjjaen.microservices.eventdriven.kafka.twitterproducer.businessObject;

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

    public static KafkaMessage builtKafkaMessageFromTweet(Tweet tweet) {
        KafkaMessage kafkaMessage = new KafkaMessage();
        kafkaMessage.setId(tweet.getId());
        kafkaMessage.setCreatedAt(tweet.getCreated_at());
        kafkaMessage.setFavorite_count(tweet.getFavorite_count());
        kafkaMessage.setFavorited(tweet.isFavorited());
        kafkaMessage.setQuote_count(tweet.getQuote_count());
        kafkaMessage.setReply_count(tweet.getReply_count());
        kafkaMessage.setRetweeted(tweet.isRetweeted());
        kafkaMessage.setText(tweet.getText());
        kafkaMessage.setUserName(tweet.getUser().getName());
        return kafkaMessage;
    }
}
