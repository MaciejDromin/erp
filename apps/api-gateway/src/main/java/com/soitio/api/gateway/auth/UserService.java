package com.soitio.api.gateway.auth;

import com.google.protobuf.Empty;
import com.soitio.api.gateway.auth.application.UserRepository;
import com.soitio.api.gateway.auth.domain.UserResource;
import com.soitio.auth.client.AuthRequest;
import com.soitio.auth.client.AuthResponse;
import com.soitio.auth.client.RefreshTokenRequest;
import com.soitio.auth.client.TokenRequest;
import com.soitio.auth.client.UpdateCurrentOrgRequest;
import com.soitio.auth.client.UserOrgRequest;
import com.soitio.auth.client.UserOrgResponse;
import com.soitio.auth.client.ValidationResponse;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.security.UnauthorizedException;
import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@ApplicationScoped
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final OrgService orgService;
    private final JWTParser tokenParser;

    public UserService(UserRepository userRepository,
                       OrgService orgService,
                       JWTParser tokenParser) {
        this.userRepository = userRepository;
        this.orgService = orgService;
        this.tokenParser = tokenParser;
    }

    public Uni<AuthResponse> authenticate(AuthRequest request) {
        return userRepository.findByEmail(request.getEmail())
                .onItem()
                .transform(u -> authenticateUser(u, request.getPassword()))
                .onFailure()
                .transform(t -> new UnauthorizedException(t.getMessage()));
    }

    public Uni<ValidationResponse> validate(TokenRequest request) {
        return verifyToken(request);
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

    private AuthResponse authenticateUser(UserResource userResource, String plainPassword) {
        if (BcryptUtil.matches(plainPassword, userResource.getPassword())) return AuthResponse.newBuilder()
                .setAuthToken(buildAuthToken(userResource))
                .setRefreshToken(buildRefreshToken(userResource))
//                .setOrgId(userResource.getCurrentOrgId()) TODO: Only for debug
                .setOrgId("5")
                .build();
        throw new UnauthorizedException("Invalid email or password");
    }

    private String buildAuthToken(UserResource userResource) {
        Instant now = Instant.now();
        return Jwt.claims()
                .expiresAt(now.plus(7, ChronoUnit.DAYS))
                .audience(userResource.getUsername())
                .issuedAt(now)
//                .issuer("soitio.com")
                .subject(userResource.getEmail())
                // TODO: Roles?
                .sign();
    }

    private String buildRefreshToken(UserResource userResource) {
        Instant now = Instant.now();
        return Jwt.claims()
                .expiresAt(now.plus(30, ChronoUnit.DAYS))
                .audience(userResource.getUsername())
                .issuedAt(now)
//                .issuer("soitio.com")
                .subject(userResource.getEmail())
                .sign();
    }

    private Uni<ValidationResponse> verifyToken(TokenRequest request) {
        JsonWebToken parsedToken;
        try {
            parsedToken = tokenParser.parse(request.getAuthToken());
            Instant now = Instant.now();
            Instant expiresAt = Instant.ofEpochMilli(parsedToken.getExpirationTime());
            if (expiresAt.isAfter(now)) return Uni.createFrom().failure(new UnauthorizedException("Token Expired!"));
        } catch (ParseException e) {
            log.warn("Couldn't parse token {}", e.getMessage());
            return Uni.createFrom().failure(new UnauthorizedException(e.getMessage()));
        }
        return userRepository.findByEmail(parsedToken.getSubject()).onItem()
                .transform(i -> ValidationResponse.newBuilder()
//                        .setOrgId(i.getCurrentOrgId())
                        .setOrgId("5")
                        .build());
    }

}
