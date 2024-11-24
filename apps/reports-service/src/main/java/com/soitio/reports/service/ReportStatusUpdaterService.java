package com.soitio.reports.service;

import com.soitio.commons.ws.ConcreteJobContent;
import com.soitio.commons.ws.EventStatus;
import com.soitio.commons.ws.WsMessage;
import com.soitio.reports.JobStatus;
import com.soitio.reports.ReportGenerationStatus;
import com.soitio.reports.service.application.ReportStatusRepository;
import com.soitio.reports.service.common.MapperUtils;
import com.soitio.reports.service.ws.WsSender;
import com.soitio.reports.service.ws.domain.CompletedReportContent;
import com.soitio.reports.service.ws.domain.FailedReportContent;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ReportStatusUpdaterService {

    private static final String WS_ENDPOINT = "com.soitio.reports.service.ws.WebsocketServer";
    private final ReportStatusRepository reportStatusRepository;
    private final WsSender wsSender;

    public ReportStatusUpdaterService(ReportStatusRepository reportStatusRepository,
                                      WsSender wsSender) {
        this.reportStatusRepository = reportStatusRepository;
        this.wsSender = wsSender;
    }

    public void updateStatus(ReportGenerationStatus reportGenerationStatus) {
        reportStatusRepository.update(reportGenerationStatus);
        switch (reportGenerationStatus.getJobStatus()) {
            case JobStatus.IN_PROGRESS -> wsSender.send(WS_ENDPOINT,
                    new WsMessage<>("event.reports", // TODO: extract to constant
                            new ConcreteJobContent(EventStatus.IN_PROGRESS,
                                    reportGenerationStatus.getJobId())));
            case JobStatus.FINISHED -> wsSender.send(WS_ENDPOINT,
                    new WsMessage<>("event.reports",
                            new CompletedReportContent(EventStatus.COMPLETED,
                                    reportGenerationStatus.getJobId(),
                                    reportGenerationStatus.getLocation())));
            case JobStatus.FAILED -> wsSender.send(WS_ENDPOINT,
                    new WsMessage<>("event.reports",
                            new FailedReportContent(EventStatus.FAILED,
                                    reportGenerationStatus.getJobId(),
                                    reportGenerationStatus.getReason())));
        }
    }

}
