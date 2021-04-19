package org.mjjaen.microservices.eventdriven.kafka.twitterproducer.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;

@Component
@PropertySources({
        @PropertySource("classpath:application.properties")
})
@Data
public class TwitterConfiguration {
    @Value("${api.key}")
    private String apiKey;

    @Value("${api.secret.key}")
    private String apiSecretKey;

    @Value("${access.token}")
    private String accessToken;

    @Value("${secret.access.token}")
    private String secretAccessToken;

    @Value("${kafka.topic}")
    private String kafkaTopic;
}
