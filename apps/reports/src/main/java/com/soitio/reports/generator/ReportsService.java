package com.soitio.reports.generator;

import com.soitio.reports.JobStatus;
import com.soitio.reports.ReportGenerationStatus;
import com.soitio.reports.ReportRequest;
import com.soitio.reports.ReportsServiceGrpc;
import io.grpc.stub.StreamObserver;
import io.quarkus.grpc.GrpcService;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

@GrpcService
public class ReportsService extends ReportsServiceGrpc.ReportsServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(ReportsService.class);

    private final ExecutorService executorService;
    private final ReportsGeneratorService reportsGeneratorService;

    public ReportsService(@Named("reportExecutor") ExecutorService executorService,
                          ReportsGeneratorService reportsGeneratorService) {
        this.executorService = executorService;
        this.reportsGeneratorService = reportsGeneratorService;
    }

    @Override
    public void generateReport(ReportRequest request, StreamObserver<ReportGenerationStatus> responseObserver) {
        String jobId = acceptGenerationRequest(request);
        responseObserver.onNext(ReportGenerationStatus.newBuilder()
                .setJobId(jobId)
                .setJobStatus(JobStatus.ACCEPTED)
                .build());
        responseObserver.onCompleted();
    }

    private String acceptGenerationRequest(ReportRequest request) {
        String jobId = UUID.randomUUID().toString();
        log.info("Accepted request to generate report {} from template {}. JobId: {}",
                request.getName(), request.getTemplate(), jobId);
        enqueueRequest(request, jobId);
        return jobId;
    }

    private void enqueueRequest(ReportRequest request, String jobId) {
        executorService.submit(() -> reportsGeneratorService.generateReportAndNotify(request, jobId));
    }

}
