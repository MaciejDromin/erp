package com.soitio.api.gateway.auth.application;

import com.soitio.api.gateway.auth.domain.UserResource;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements ReactivePanacheMongoRepository<UserResource> {


    public Uni<UserResource> findByEmail(String email) {
        return find("email", email).firstResult();
    }
}
