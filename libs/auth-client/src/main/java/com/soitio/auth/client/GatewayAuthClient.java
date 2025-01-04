package com.soitio.auth.client;

import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class GatewayAuthClient {

    private final AuthServiceGrpc.AuthServiceStub stub;

    public GatewayAuthClient(String host, int port) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext().build();
        this.stub = AuthServiceGrpc.newStub(channel);
    }

    public void authenticate(AuthRequest authRequest, StreamObserver<AuthResponse> observer) {
        this.stub.authenticate(authRequest, observer);
    }

    public void validate(TokenRequest tokenRequest, StreamObserver<Empty> observer) {
        this.stub.validate(tokenRequest, observer);
    }

    public void getOrgsForUser(UserOrgRequest userOrgRequest, StreamObserver<UserOrgResponse> observer) {
        this.stub.getOrgsForUser(userOrgRequest, observer);
    }

    public void updateCurrentlyUsedOrg(UpdateCurrentOrgRequest request, StreamObserver<Empty> observer) {
        this.stub.updateCurrentlyUsedOrg(request, observer);
    }

    public void refreshToken(RefreshTokenRequest request, StreamObserver<AuthResponse> observer) {
        this.stub.refreshToken(request, observer);
    }

}
