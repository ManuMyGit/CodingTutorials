package org.mjjaen.microservices.eventdriven.kafka.elasticssearchconsumer.configuration;

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
public class ElasticSearchConfiguration {
    @Value("${elasticsearch.hostname}")
    private String hostname;
    @Value("${elasticsearch.port}")
    private int port;
    @Value("${elasticsearch.schema}")
    private String schema;
    @Value("${elasticsearch.user}")
    private String username;
    @Value("${elasticsearch.password}")
    private String password;
}