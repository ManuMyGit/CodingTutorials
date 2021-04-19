package org.mjjaen.microservices.eventdriven.kafka.twitterproducer.businessObject;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.Data;

@Data
@JsonIncludeProperties({"id", "name", "verified", "followers_count", "friends_count", "favourites_count", "created_at"})
public class User {
    private long id;
    private String name;
    private boolean verified;
    private int followers_count;
    private int friends_count;
    private int favourites_count;
    private String created_at;
}
