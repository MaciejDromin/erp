package com.soitio.api.gateway;

import com.soitio.api.gateway.domain.auth.AuthRequestDto;
import com.soitio.api.gateway.domain.auth.AuthResponseDto;
import com.soitio.auth.client.AuthRequest;
import com.soitio.auth.client.AuthResponse;
import com.soitio.auth.client.AuthService;
import io.quarkus.grpc.GrpcClient;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

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

    private AuthRequest mapAuthRequest(AuthRequestDto authRequest) {
        return AuthRequest.newBuilder()
                .setEmail(authRequest.email())
                .setPassword(authRequest.password())
                .build();
    }

    private AuthResponseDto mapAuthResponse(AuthResponse authResponse) {
        return new AuthResponseDto(authResponse.getAuthToken(), authResponse.getRefreshToken(), authResponse.getOrgId());
    }

}
