package com.soitio.api.gateway.auth.application;

import com.soitio.api.gateway.auth.domain.OrgResource;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OrgRepository implements ReactivePanacheMongoRepository<OrgResource> {



}
