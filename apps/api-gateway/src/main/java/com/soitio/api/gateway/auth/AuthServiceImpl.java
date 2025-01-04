package com.soitio.api.gateway.auth;

import com.google.protobuf.Empty;
import com.soitio.auth.client.AuthRequest;
import com.soitio.auth.client.AuthResponse;
import com.soitio.auth.client.AuthServiceGrpc;
import com.soitio.auth.client.RefreshTokenRequest;
import com.soitio.auth.client.TokenRequest;
import com.soitio.auth.client.UpdateCurrentOrgRequest;
import com.soitio.auth.client.UserOrgRequest;
import com.soitio.auth.client.UserOrgResponse;
import io.grpc.stub.StreamObserver;
import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;
import java.util.function.Function;

@GrpcService
public class AuthServiceImpl extends AuthServiceGrpc.AuthServiceImplBase {

    private final UserService userService;

    public AuthServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void authenticate(AuthRequest request, StreamObserver<AuthResponse> responseObserver) {
        handleRequest(request, responseObserver, userService::authenticate);
    }

    @Override
    public void validate(TokenRequest request, StreamObserver<Empty> responseObserver) {
        handleRequest(request, responseObserver, userService::validate);
    }

    @Override
    public void getOrgsForUser(UserOrgRequest request, StreamObserver<UserOrgResponse> responseObserver) {
        handleRequest(request, responseObserver, userService::getOrgsForUser);
    }

    @Override
    public void updateCurrentlyUsedOrg(UpdateCurrentOrgRequest request, StreamObserver<Empty> responseObserver) {
        handleRequest(request, responseObserver, userService::updateCurrentlyUsedOrg);
    }

    @Override
    public void refreshToken(RefreshTokenRequest request, StreamObserver<AuthResponse> responseObserver) {
        handleRequest(request, responseObserver, userService::refreshToken);
    }

    private <REQ, RES> void handleRequest(REQ request, StreamObserver<RES> observer, Function<REQ, Uni<RES>> func) {
        func.apply(request).subscribe().with(i -> {
            observer.onNext(i);
            observer.onCompleted();
        }, e -> {
            observer.onError(e);
            throw new RuntimeException(e);
        });
    }

}
