package com.soitio.api.gateway.auth;

import com.google.protobuf.Empty;
import com.soitio.api.gateway.auth.application.UserRepository;
import com.soitio.api.gateway.auth.domain.Pair;
import com.soitio.api.gateway.auth.domain.UserResource;
import com.soitio.auth.client.AuthRequest;
import com.soitio.auth.client.AuthResponse;
import com.soitio.auth.client.OrgDetails;
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
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

@ApplicationScoped
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private static final int AUTH_TOKEN_DEFAULT_PERIOD = 7;
    private static final int REFRESH_TOKEN_DEFAULT_PERIOD = 30;

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
                    .transform(UserResource::getOrgs)
                    .onItem()
                    .transformToUni(orgService::findByOrgIds)
                    .onItem()
                    .transform(ol -> UserOrgResponse.newBuilder()
                            .addAllOrgs(ol.stream()
                                    .map(or -> OrgDetails.newBuilder()
                                            .setOrgId(or.getOrgId())
                                            .setName(or.getName())
                                            .build())
                                    .toList())
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
                    .transform(u -> {
                        Pair<String, Instant> authTokenPair = buildAuthToken(u);
                        return AuthResponse.newBuilder()
                                .setAuthToken(authTokenPair.left())
                                .setRefreshToken(request.getRefreshToken())
                                .setOrgId(u.getCurrentOrgId())
                                .setExpiresIn(authTokenPair.right().getLong(ChronoField.INSTANT_SECONDS))
                                .build();
                    });
        } catch (UnauthorizedException e) {
            return Uni.createFrom().failure(e);
        }
    }

    private AuthResponse authenticateUser(UserResource userResource, String plainPassword) {
        if (BcryptUtil.matches(plainPassword, userResource.getPassword())) {
            Pair<String, Instant> authTokenPair = buildAuthToken(userResource);
            return AuthResponse.newBuilder()
                    .setAuthToken(authTokenPair.left())
                    .setRefreshToken(buildRefreshToken(userResource))
                    .setOrgId(userResource.getCurrentOrgId())
                    .setExpiresIn(authTokenPair.right().getLong(ChronoField.INSTANT_SECONDS))
                    .build();
        }
        throw new UnauthorizedException("Invalid email or password");
    }

    private Pair<String, Instant> buildAuthToken(UserResource userResource) {
        Instant now = Instant.now();
        Instant expiresAt = now.plus(AUTH_TOKEN_DEFAULT_PERIOD, ChronoUnit.DAYS);
        return new Pair<>(Jwt.claims()
                .expiresAt(expiresAt)
                .audience(userResource.getUsername())
                .issuedAt(now)
                .subject(userResource.getEmail())
                // TODO: Roles?
                .sign(), expiresAt);
    }

    private String buildRefreshToken(UserResource userResource) {
        Instant now = Instant.now();
        return Jwt.claims()
                .expiresAt(now.plus(REFRESH_TOKEN_DEFAULT_PERIOD, ChronoUnit.DAYS))
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
