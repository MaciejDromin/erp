package com.soitio.api.gateway.web;

import com.soitio.api.gateway.GatewayAuthService;
import com.soitio.api.gateway.domain.auth.AuthRequestDto;
import com.soitio.api.gateway.domain.auth.AuthResponseDto;
import com.soitio.api.gateway.domain.auth.UserOrgResponseDto;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.HttpHeaders;

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

    @GET
    @Path("/get-orgs")
    public Uni<UserOrgResponseDto> getOrgs(HttpHeaders headers) {
        return authService.getOrgsForUser(headers);
    }

    @POST
    @Path("/token")
    public Uni<AuthResponseDto> token(HttpHeaders headers) {
        return authService.refreshToken(headers);
    }

    @POST
    @Path("/current-org-update")
    public Uni<Void> updateCurrentOrg(@QueryParam("newOrgId") String orgId, HttpHeaders headers) {
        return authService.updateCurrentOrg(orgId, headers);
    }

}
