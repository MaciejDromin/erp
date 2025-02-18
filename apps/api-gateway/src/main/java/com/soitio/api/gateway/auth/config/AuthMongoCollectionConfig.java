package com.soitio.api.gateway.auth.config;

import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import io.quarkus.mongodb.reactive.ReactiveMongoClient;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

@ApplicationScoped
public class AuthMongoCollectionConfig {

    private final ReactiveMongoClient client;

    public AuthMongoCollectionConfig(ReactiveMongoClient client) {
        this.client = client;
    }

    public void onStartup(@Observes StartupEvent event) {
        client.getDatabase("auth")
                .getCollection("OrgResource")
                .createIndex(Indexes.ascending("orgId"),
                        new IndexOptions().name("uniqueOrgId").unique(true));
        client.getDatabase("auth")
                .getCollection("UserResource")
                .createIndex(Indexes.ascending("email"),
                        new IndexOptions().name("uniqueEmail").unique(true));
    }

}
