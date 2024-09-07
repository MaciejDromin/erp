package com.soitio.reports.service.application;

import com.soitio.reports.ReportGenerationStatus;
import com.soitio.reports.service.domain.ReportStatus;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ReportStatusRepository implements PanacheRepository<ReportStatus> {

    @Transactional
    public void create(ReportGenerationStatus reportGenerationStatus) {
        persist(to(reportGenerationStatus));
    }

    @Transactional
    public void update(ReportGenerationStatus reportGenerationStatus) {
        ReportStatus reportStatus = find("jobId = ?1", reportGenerationStatus.getJobId()).firstResult();
        update(reportStatus, reportGenerationStatus);
        persist(reportStatus);
    }

    private ReportStatus to(ReportGenerationStatus reportGenerationStatus) {
        return ReportStatus.builder()
                .jobId(reportGenerationStatus.getJobId())
                .status(reportGenerationStatus.getJobStatus())
                .build();
    }

    private void update(ReportStatus oldStatus, ReportGenerationStatus newStatus) {
        oldStatus.setStatus(newStatus.getJobStatus());
        oldStatus.setLocation(newStatus.getLocation());
        oldStatus.setMessage(newStatus.getReason());
    }

}
