package org.mjjaen.mongo.configuration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import org.mjjaen.mongo.converter.*;
import org.mjjaen.mongo.listener.EventListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

@Configuration
public class DataBaseConfig extends AbstractMongoClientConfiguration  {
    @Value("${spring.data.mongodb.uri}")
    private String uri;

    @Value("${spring.data.mongodb.database}")
    private String db;

    @Override
    protected void configureClientSettings(MongoClientSettings.Builder builder) {
        final ConnectionString connectionString = new ConnectionString(uri);
        builder.applyConnectionString(connectionString);
    }

    //Bean required to enable transactionality
    @Bean
    MongoTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }

    @Override
    protected String getDatabaseName() {
        return db;
    }

    //Converters disabled so DBRef works
    protected void configureConverters(MongoCustomConversions.MongoConverterConfigurationAdapter converterConfigurationAdapter) {
        //converterConfigurationAdapter.registerConverter(new UserReaderConverter());
        //converterConfigurationAdapter.registerConverter(new UserWriterConverter());
        //converterConfigurationAdapter.registerConverter(new BookReaderConverter());
        //converterConfigurationAdapter.registerConverter(new BookWriterConverter());
    }

    //Bean required to enable register the bean in charge of listening to events.
    @Bean
    public EventListener eventListener() {
        return new EventListener();
    }
}
