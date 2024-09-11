package com.soitio.reports.service;

import com.google.protobuf.Empty;
import com.soitio.reports.ReportGenerationStatus;
import com.soitio.reports.ReportsStatusServiceGrpc;
import io.grpc.stub.StreamObserver;
import io.quarkus.grpc.GrpcService;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.ExecutorService;

@GrpcService
public class ReportsStatusService extends ReportsStatusServiceGrpc.ReportsStatusServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(ReportsStatusService.class);

    private final ExecutorService executorService;
    private final ReportStatusUpdaterService reportStatusUpdaterService;

    public ReportsStatusService(@Named("reportStatusExecutor") ExecutorService executorService,
                                ReportStatusUpdaterService reportStatusUpdaterService) {
        this.executorService = executorService;
        this.reportStatusUpdaterService = reportStatusUpdaterService;
    }

    @Override
    public void updateReportStatus(ReportGenerationStatus request, StreamObserver<Empty> responseObserver) {
        enqueueRequest(request);
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    private void enqueueRequest(ReportGenerationStatus request) {
        log.info("Report Generation status update for job: {}, new status: {}",
                request.getJobId(), request.getJobStatus());
        executorService.submit(() -> reportStatusUpdaterService.updateStatus(request));
    }

}
