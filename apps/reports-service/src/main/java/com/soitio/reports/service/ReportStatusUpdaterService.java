package com.soitio.reports.service;

import com.soitio.reports.ReportGenerationStatus;
import com.soitio.reports.service.application.ReportStatusRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ReportStatusUpdaterService {

    private final ReportStatusRepository reportStatusRepository;

    public ReportStatusUpdaterService(ReportStatusRepository reportStatusRepository) {
        this.reportStatusRepository = reportStatusRepository;
    }

    public void updateStatus(ReportGenerationStatus reportGenerationStatus) {
        reportStatusRepository.update(reportGenerationStatus);
    }

}
