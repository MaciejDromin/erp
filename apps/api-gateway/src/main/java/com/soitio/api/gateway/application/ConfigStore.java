package com.soitio.api.gateway.application;

import com.soitio.api.gateway.domain.ConfigResource;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ConfigStore implements ReactivePanacheMongoRepository<ConfigResource> {

}
