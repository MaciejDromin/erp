package com.soitio.reports.generator;

import com.soitio.reports.JobStatus;
import com.soitio.reports.ReportGenerationStatus;
import com.soitio.reports.ReportRequest;
import com.soitio.reports.client.ReportStatusClient;
import com.soitio.reports.generator.client.ArtifactClient;
import com.soitio.reports.generator.templates.TemplateService;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.FileInputStream;
import java.io.IOException;

@ApplicationScoped
public class ReportsGeneratorService {

    private static final Logger log = LoggerFactory.getLogger(ReportsGeneratorService.class);

    private final TemplateService templateService;
    private final PDFService pdfService;
    private final ReportStatusClient reportStatusClient;
    private final ArtifactClient artifactClient;

    public ReportsGeneratorService(TemplateService templateService,
                                   PDFService pdfService,
                                   ReportStatusClient reportStatusClient,
                                   @RestClient ArtifactClient artifactClient) {
        this.templateService = templateService;
        this.pdfService = pdfService;
        this.reportStatusClient = reportStatusClient;
        this.artifactClient = artifactClient;
    }

    public void generateReportAndNotify(ReportRequest request, String jobId) {
        log.info("Starting generating report, jobId: {}", jobId);
        reportStatusClient.updateStatus(ReportGenerationStatus.newBuilder()
                .setJobId(jobId)
                .setJobStatus(JobStatus.IN_PROGRESS)
                .build());

        String location;

        try {
            location = generateReport(request);
        } catch (Exception e) {
            log.warn("Failed to generate report for jobId: {}", jobId);
            reportStatusClient.updateStatus(ReportGenerationStatus.newBuilder()
                    .setJobId(jobId)
                    .setJobStatus(JobStatus.FAILED)
                    .setReason(e.getMessage())
                    .build());
            return;
        }

        log.info("Finished generating report, jobId: {}", jobId);
        reportStatusClient.updateStatus(ReportGenerationStatus.newBuilder()
                .setJobId(jobId)
                .setJobStatus(JobStatus.FINISHED)
                .setLocation(location)
                .build());
    }

    public String generateReport(ReportRequest request) throws Exception {
        String rendered = templateService.renderFromTemplate(request.getTemplate(), request.getDataMap());

        String filename = pdfService.generatePdf(request.getName(), rendered);

        try (FileInputStream is = new FileInputStream(filename)){
            return artifactClient.uploadArtifact(request.getOrgId(), filename, "generated", is);
        } catch (IOException e) {
            throw new IllegalStateException("Could not find file to archive");
        }
    }

}
