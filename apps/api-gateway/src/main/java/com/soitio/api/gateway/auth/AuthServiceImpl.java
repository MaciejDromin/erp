package com.soitio.api.gateway.auth;

import com.google.protobuf.Empty;
import com.soitio.auth.client.AuthRequest;
import com.soitio.auth.client.AuthResponse;
import com.soitio.auth.client.AuthService;
import com.soitio.auth.client.RefreshTokenRequest;
import com.soitio.auth.client.TokenRequest;
import com.soitio.auth.client.UpdateCurrentOrgRequest;
import com.soitio.auth.client.UserOrgRequest;
import com.soitio.auth.client.UserOrgResponse;
import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;

@GrpcService
public class AuthServiceImpl implements AuthService {

    private final UserService userService;

    public AuthServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Uni<AuthResponse> authenticate(AuthRequest request) {
        return userService.authenticate(request);
    }

    @Override
    public Uni<Empty> validate(TokenRequest request) {
        return userService.validate(request);
    }

    @Override
    public Uni<UserOrgResponse> getOrgsForUser(UserOrgRequest request) {
        return userService.getOrgsForUser(request);
    }

    @Override
    public Uni<Empty> updateCurrentlyUsedOrg(UpdateCurrentOrgRequest request) {
        return userService.updateCurrentlyUsedOrg(request);
    }

    @Override
    public Uni<AuthResponse> refreshToken(RefreshTokenRequest request) {
        return userService.refreshToken(request);
    }
}
