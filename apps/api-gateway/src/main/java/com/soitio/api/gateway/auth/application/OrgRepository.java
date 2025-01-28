package com.soitio.api.gateway.auth.application;

import com.soitio.api.gateway.auth.domain.OrgResource;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Set;

@ApplicationScoped
public class OrgRepository implements ReactivePanacheMongoRepository<OrgResource> {

    public Uni<List<OrgResource>> findByOrgIds(Set<String> orgIds) {
        return find("orgId in ?1", orgIds).list();
    }
    
}
