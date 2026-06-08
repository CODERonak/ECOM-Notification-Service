package com.ecom.notificationservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Override
    protected String getDatabaseName() {
        return "NOTIFICATIONS";
    }

    @Override
    public MongoClient mongoClient() {
        // Had some issues in connecting to Mongo DB Atlas so i had to use MongoClient config
        return MongoClients.create(mongoUri);
    }
}
