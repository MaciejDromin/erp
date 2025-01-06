package com.soitio.api.gateway.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

@ApplicationScoped
public class MongoCollectionConfig {

    private final MongoClient client;

    public MongoCollectionConfig(MongoClient client) {
        this.client = client;
    }

    public void onStartup(@Observes StartupEvent event) {
        client.getDatabase("gateway")
                .getCollection("ConfigResource")
                .createIndex(Indexes.ascending("key"),
                        new IndexOptions().name("uniqueKey").unique(true));
    }

}
