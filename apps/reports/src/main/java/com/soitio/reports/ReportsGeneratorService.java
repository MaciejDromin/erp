package com.soitio.reports;

import com.soitio.reports.client.ReportStatusClient;
import com.soitio.reports.sftp.SftpConnectionDetails;
import com.soitio.reports.sftp.SftpService;
import com.soitio.reports.templates.TemplateService;
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

    public void generateReport(ReportRequest request, String jobId) {
        log.info("Starting generating report, jobId: {}", jobId);
//        reportStatusClient.updateStatus(ReportGenerationStatus.newBuilder()
//                .setJobId(jobId)
//                .setJobStatus(JobStatus.IN_PROGRESS)
//                .build());

        String rendered = templateService.renderFromTemplate(request.getTemplate(), request.getDataMap());

        String filePath = pdfService.generatePdf(request.getName(), rendered);

        sftpService.archiveFile(filePath, sftpConnectionDetails);

        log.info("Finished generating report, jobId: {}", jobId);
//        reportStatusClient.updateStatus(ReportGenerationStatus.newBuilder()
//                .setJobId(jobId)
//                .setJobStatus(JobStatus.FINISHED)
//                .build());
    }

}
