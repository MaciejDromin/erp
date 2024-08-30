package com.soitio.reports;

import com.soitio.reports.client.ReportStatusClient;
import com.soitio.reports.templates.TemplateService;
import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class ReportsGeneratorService {

    private static final Logger log = LoggerFactory.getLogger(ReportsGeneratorService.class);

    private final TemplateService templateService;
    private final PDFService pdfService;
    private final SftpService sftpService;
    private final ReportStatusClient reportStatusClient;

    public ReportsGeneratorService(TemplateService templateService,
                                   PDFService pdfService,
                                   SftpService sftpService,
                                   ReportStatusClient reportStatusClient) {
        this.templateService = templateService;
        this.pdfService = pdfService;
        this.sftpService = sftpService;
        this.reportStatusClient = reportStatusClient;
    }

    public void generateReport(ReportRequest request, String jobId) {
        log.info("Starting generating report, jobId: {}", jobId);
//        reportStatusClient.updateStatus(ReportGenerationStatus.newBuilder()
//                .setJobId(jobId)
//                .setJobStatus(JobStatus.IN_PROGRESS)
//                .build());

        String rendered = templateService.renderFromTemplate(request.getTemplate(), request.getDataMap());

        String filePath = pdfService.generatePdf(request.getName(), rendered);

        sftpService.archiveFile(filePath);

        log.info("Finished generating report, jobId: {}", jobId);
//        reportStatusClient.updateStatus(ReportGenerationStatus.newBuilder()
//                .setJobId(jobId)
//                .setJobStatus(JobStatus.FINISHED)
//                .build());
    }

}
