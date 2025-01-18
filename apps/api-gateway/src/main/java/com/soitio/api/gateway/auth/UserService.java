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
        try {
            JsonWebToken parsedToken = validateToken(request.getAuthToken());
            return userRepository.findByEmail(parsedToken.getSubject()).onItem()
                    .transform(u -> UserOrgResponse.newBuilder()
                            .addAllOrgs(u.getOrgs())
                            .build());
        } catch (UnauthorizedException e) {
            return Uni.createFrom().failure(e);
        }
    }

    public Uni<Empty> updateCurrentlyUsedOrg(UpdateCurrentOrgRequest request) {
        try {
            JsonWebToken parsedToken = validateToken(request.getAuthToken());
            return userRepository.findByEmail(parsedToken.getSubject()).onItem()
                    .call(u -> {
                        u.setCurrentOrgId(request.getOrgId());
                        return userRepository.update(u);
                    }).onItem().transform(u -> Empty.newBuilder().build());
        } catch (UnauthorizedException e) {
            return Uni.createFrom().failure(e);
        }
    }

    public Uni<AuthResponse> refreshToken(RefreshTokenRequest request) {
        try {
            JsonWebToken parsedToken = validateToken(request.getRefreshToken());
            return userRepository.findByEmail(parsedToken.getSubject()).onItem()
                    .transform(u -> AuthResponse.newBuilder()
                            .setAuthToken(buildAuthToken(u))
                            .setRefreshToken(request.getRefreshToken())
                            .setOrgId(u.getCurrentOrgId())
                            .build());
        } catch (UnauthorizedException e) {
            return Uni.createFrom().failure(e);
        }
    }

    private AuthResponse authenticateUser(UserResource userResource, String plainPassword) {
        if (BcryptUtil.matches(plainPassword, userResource.getPassword())) return AuthResponse.newBuilder()
                .setAuthToken(buildAuthToken(userResource))
                .setRefreshToken(buildRefreshToken(userResource))
                .setOrgId(userResource.getCurrentOrgId())
                .build();
        throw new UnauthorizedException("Invalid email or password");
    }

    private String buildAuthToken(UserResource userResource) {
        Instant now = Instant.now();
        return Jwt.claims()
                .expiresAt(now.plus(7, ChronoUnit.DAYS))
                .audience(userResource.getUsername())
                .issuedAt(now)
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
                .subject(userResource.getEmail())
                .sign();
    }

    private Uni<ValidationResponse> verifyToken(TokenRequest request) {
        try {
            JsonWebToken parsedToken = validateToken(request.getAuthToken());
            return userRepository.findByEmail(parsedToken.getSubject()).onItem()
                    .transform(i -> ValidationResponse.newBuilder()
                            .setOrgId(i.getCurrentOrgId())
                            .build());
        } catch (UnauthorizedException e) {
            return Uni.createFrom().failure(e);
        }
    }

    private JsonWebToken validateToken(String token) throws UnauthorizedException {
        JsonWebToken parsedToken;
        try {
            parsedToken = tokenParser.parse(token);
            Instant now = Instant.now();
            Instant expiresAt = Instant.ofEpochMilli(parsedToken.getExpirationTime());
            if (expiresAt.isAfter(now)) throw new UnauthorizedException("Token Expired!");
        } catch (ParseException e) {
            log.warn("Couldn't parse token {}", e.getMessage());
            throw new UnauthorizedException(e.getMessage());
        }
        return parsedToken;
    }

}
