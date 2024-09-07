package com.soitio.reports.generator;

import com.soitio.reports.JobStatus;
import com.soitio.reports.ReportGenerationStatus;
import com.soitio.reports.ReportRequest;
import com.soitio.reports.client.ReportStatusClient;
import com.soitio.reports.generator.sftp.SftpConnectionDetails;
import com.soitio.reports.generator.sftp.SftpService;
import com.soitio.reports.generator.templates.TemplateService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class ReportsGeneratorService {

    private static final Logger log = LoggerFactory.getLogger(ReportsGeneratorService.class);

    private final TemplateService templateService;
    private final PDFService pdfService;
    private final SftpService sftpService;
    private final ReportStatusClient reportStatusClient;
    private final SftpConnectionDetails sftpConnectionDetails;

    public ReportsGeneratorService(TemplateService templateService,
                                   PDFService pdfService,
                                   SftpService sftpService,
                                   ReportStatusClient reportStatusClient,
                                   @Named("defaultSftp") SftpConnectionDetails sftpConnectionDetails) {
        this.templateService = templateService;
        this.pdfService = pdfService;
        this.sftpService = sftpService;
        this.reportStatusClient = reportStatusClient;
        this.sftpConnectionDetails = sftpConnectionDetails;
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

        String filePath = pdfService.generatePdf(request.getName(), rendered);

        return sftpService.archiveFile(filePath, sftpConnectionDetails);
    }

}
