package com.soitio.api.gateway;

import com.soitio.api.gateway.domain.auth.AuthRequestDto;
import com.soitio.api.gateway.domain.auth.AuthResponseDto;
import com.soitio.api.gateway.domain.auth.UserOrgResponseDto;
import com.soitio.api.gateway.utils.AuthUtils;
import com.soitio.auth.client.AuthRequest;
import com.soitio.auth.client.AuthResponse;
import com.soitio.auth.client.AuthService;
import com.soitio.auth.client.RefreshTokenRequest;
import com.soitio.auth.client.UpdateCurrentOrgRequest;
import com.soitio.auth.client.UserOrgRequest;
import com.soitio.auth.client.UserOrgResponse;
import io.quarkus.grpc.GrpcClient;
import io.quarkus.security.UnauthorizedException;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.HttpHeaders;

@ApplicationScoped
public class GatewayAuthService {

    private final AuthService authService;

    public GatewayAuthService(@GrpcClient AuthService authService) {
        this.authService = authService;
    }

    public Uni<AuthResponseDto> authenticate(AuthRequestDto authRequest) {
        return authService.authenticate(mapAuthRequest(authRequest))
                .onItem()
                .transform(this::mapAuthResponse)
                .onFailure()
                .transform(t -> t); // TODO: Handle failure
    }

    public Uni<UserOrgResponseDto> getOrgsForUser(HttpHeaders headers) {
        String token = AuthUtils.extractAuthorizationHeader(headers);
        if (token == null) return Uni.createFrom().failure(new UnauthorizedException("Missing Authorization header"));
        return authService.getOrgsForUser(UserOrgRequest.newBuilder()
                        .setAuthToken(AuthUtils.extractToken(token))
                        .build()).onItem()
                .transform(this::mapOrgResponse);
    }

    public Uni<AuthResponseDto> refreshToken(HttpHeaders headers) {
        String token = AuthUtils.extractRefreshTokenHeader(headers);
        if (token == null) return Uni.createFrom().failure(new UnauthorizedException("Missing Refresh-Token header"));
        return authService.refreshToken(RefreshTokenRequest.newBuilder()
                        .setRefreshToken(AuthUtils.extractToken(token))
                        .build()).onItem()
                .transform(this::mapAuthResponse);
    }

    public Uni<Void> updateCurrentOrg(String orgId, HttpHeaders headers) {
        String token = AuthUtils.extractAuthorizationHeader(headers);
        if (token == null) return Uni.createFrom().failure(new UnauthorizedException("Missing Authorization header"));
        return authService.updateCurrentlyUsedOrg(UpdateCurrentOrgRequest.newBuilder()
                        .setOrgId(orgId)
                        .setAuthToken(AuthUtils.extractToken(token))
                        .build()).onItem()
                .transformToUni(i -> Uni.createFrom().voidItem());
    }

    private UserOrgResponseDto mapOrgResponse(UserOrgResponse userOrgResponse) {
        return new UserOrgResponseDto(userOrgResponse.getOrgsList().stream().toList());
    }

    private AuthResponseDto mapAuthResponse(AuthResponse authResponse) {
        return new AuthResponseDto(authResponse.getAuthToken(),
                authResponse.getRefreshToken(),
                authResponse.getOrgId(),
                authResponse.getExpiresIn());
    }

    private AuthRequest mapAuthRequest(AuthRequestDto authRequest) {
        return AuthRequest.newBuilder()
                .setEmail(authRequest.email())
                .setPassword(authRequest.password())
                .build();
    }

}
