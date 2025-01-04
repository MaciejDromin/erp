package com.soitio.api.gateway.auth;

import com.soitio.api.gateway.auth.application.OrgRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OrgService {

    private final OrgRepository orgRepository;

    public OrgService(OrgRepository orgRepository) {
        this.orgRepository = orgRepository;
    }

}
