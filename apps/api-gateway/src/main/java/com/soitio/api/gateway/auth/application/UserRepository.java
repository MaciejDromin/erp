package com.soitio.api.gateway.auth.application;

import com.soitio.api.gateway.auth.domain.UserResource;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements ReactivePanacheMongoRepository<UserResource> {



}
