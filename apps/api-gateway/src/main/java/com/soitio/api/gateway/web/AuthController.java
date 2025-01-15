package com.soitio.api.gateway.web;

import com.soitio.api.gateway.GatewayAuthService;
import com.soitio.api.gateway.domain.auth.AuthRequestDto;
import com.soitio.api.gateway.domain.auth.AuthResponseDto;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/")
public class AuthController {

    private final GatewayAuthService authService;

    public AuthController(GatewayAuthService authService) {
        this.authService = authService;
    }

    @POST
    @Path("/login")
    public Uni<AuthResponseDto> authenticate(AuthRequestDto request) {
        return authService.authenticate(request);
    }

}
