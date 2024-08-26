package com.soitio.reports.client;

import com.soitio.reports.ReportRequest;
import com.soitio.reports.ReportsServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class ReportsClient {

    private final ReportsServiceGrpc.ReportsServiceBlockingStub stub;

    public ReportsClient(String host, int port) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext().build();
        this.stub = ReportsServiceGrpc.newBlockingStub(channel);
    }

    public void generateReport(ReportRequest request) {
        this.stub.generateReport(request);
    }

}
