package com.soitio.reports.client;

import com.soitio.reports.ReportGenerationStatus;
import com.soitio.reports.ReportsStatusServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class ReportStatusClient {

    private final ReportsStatusServiceGrpc.ReportsStatusServiceBlockingStub stub;

    public ReportStatusClient(String host, int port) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext().build();
        this.stub = ReportsStatusServiceGrpc.newBlockingStub(channel);
    }

    public void updateStatus(ReportGenerationStatus request) {
        this.stub.updateReportStatus(request);
    }

}
