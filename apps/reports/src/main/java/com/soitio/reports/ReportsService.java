package com.soitio.reports;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import io.quarkus.grpc.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class ReportsService extends ReportsServiceGrpc.ReportsServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(ReportsService.class);

    @Override
    public void generateReport(ReportRequest request, StreamObserver<Empty> responseObserver) {
        log.info("ML --- INFO {}", request);
        log.info("ML ---- INFO {}", request.getDataMap());
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

}
