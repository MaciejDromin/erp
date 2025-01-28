package com.soitio.api.gateway.auth;

import com.soitio.api.gateway.auth.application.OrgRepository;
import com.soitio.api.gateway.auth.domain.OrgResource;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.subscription.UniEmitter;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Set;

@ApplicationScoped
public class OrgService {

    private final OrgRepository orgRepository;

    public OrgService(OrgRepository orgRepository) {
        this.orgRepository = orgRepository;
    }

    public Uni<List<OrgResource>> findByOrgIds(Set<String> orgIds) {
        return orgRepository.findByOrgIds(orgIds);
    }
}
