package com.soitio.api.gateway.application;

import com.soitio.api.gateway.domain.PathResource;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PathResourceRepository implements PanacheRepository<PathResource> {

    @WithSession
    public Uni<PathResource> getByPath(String path) {
        return find("path", path).firstResult();
    }

}
