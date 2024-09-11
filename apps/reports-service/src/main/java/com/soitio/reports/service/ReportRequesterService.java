package com.soitio.reports.service;

import com.soitio.reports.ReportGenerationStatus;
import com.soitio.reports.ReportRequest;
import com.soitio.reports.Value;
import com.soitio.reports.client.ReportsClient;
import com.soitio.reports.service.application.ReportStatusRepository;
import com.soitio.reports.service.domain.ReportGenerationRequestDto;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Map;

@ApplicationScoped
public class ReportRequesterService {

    private final ReportsClient reportsClient;
    private final ReportStatusRepository reportStatusRepository;

    public ReportRequesterService(ReportsClient reportsClient, ReportStatusRepository reportStatusRepository) {
        this.reportsClient = reportsClient;
        this.reportStatusRepository = reportStatusRepository;
    }

    public void requestReportGeneration(ReportGenerationRequestDto reportGenerationRequest, Map<String, Value> data) {
        ReportRequest request = to(reportGenerationRequest, data);
        ReportGenerationStatus status = reportsClient.generateReport(request);
        reportStatusRepository.create(status);
    }

    private ReportRequest to(ReportGenerationRequestDto reportGenerationRequest, Map<String, Value> data) {
        return ReportRequest.newBuilder()
                .setName(reportGenerationRequest.getName())
                .setTemplate(reportGenerationRequest.getTemplate())
                .putAllData(data)
                .build();
    }

}
