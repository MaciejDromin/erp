package com.soitio.api.gateway.auth;

import com.google.protobuf.Empty;
import com.soitio.api.gateway.auth.application.UserRepository;
import com.soitio.auth.client.AuthRequest;
import com.soitio.auth.client.AuthResponse;
import com.soitio.auth.client.RefreshTokenRequest;
import com.soitio.auth.client.TokenRequest;
import com.soitio.auth.client.UpdateCurrentOrgRequest;
import com.soitio.auth.client.UserOrgRequest;
import com.soitio.auth.client.UserOrgResponse;
import com.soitio.auth.client.ValidationResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final OrgService orgService;

    public UserService(UserRepository userRepository, OrgService orgService) {
        this.userRepository = userRepository;
        this.orgService = orgService;
    }

    public Uni<AuthResponse> authenticate(AuthRequest request) {
        return null;
    }

    public Uni<ValidationResponse> validate(TokenRequest request) {
        log.info("Hello! {}", request);
        return Uni.createFrom().item(ValidationResponse.newBuilder().build());
    }

    public Uni<UserOrgResponse> getOrgsForUser(UserOrgRequest request) {
        return null;
    }

    public Uni<Empty> updateCurrentlyUsedOrg(UpdateCurrentOrgRequest request) {
        return null;
    }

    public Uni<AuthResponse> refreshToken(RefreshTokenRequest request) {
        return null;
    }
}
