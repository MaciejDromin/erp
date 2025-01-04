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
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserService {

    private final UserRepository userRepository;
    private final OrgService orgService;

    public UserService(UserRepository userRepository, OrgService orgService) {
        this.userRepository = userRepository;
        this.orgService = orgService;
    }

    public Uni<AuthResponse> authenticate(AuthRequest request) {
        return null;
    }

    public Uni<Empty> validate(TokenRequest request) {
        return null;
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
