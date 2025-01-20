package com.soitio.reports.service;

import com.soitio.commons.ws.ConcreteJobContent;
import com.soitio.commons.ws.EventStatus;
import com.soitio.commons.ws.WsMessage;
import com.soitio.reports.ReportGenerationStatus;
import com.soitio.reports.ReportRequest;
import com.soitio.reports.Value;
import com.soitio.reports.client.ReportsClient;
import com.soitio.reports.service.application.ReportStatusRepository;
import com.soitio.reports.service.domain.ReportGenerationRequestDto;
import com.soitio.reports.service.ws.WsSender;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Map;

@ApplicationScoped
public class ReportRequesterService {

    private final ReportsClient reportsClient;
    private final ReportStatusRepository reportStatusRepository;
    private final WsSender wsSender;

    public ReportRequesterService(ReportsClient reportsClient,
                                  ReportStatusRepository reportStatusRepository,
                                  WsSender wsSender) {
        this.reportsClient = reportsClient;
        this.reportStatusRepository = reportStatusRepository;
        this.wsSender = wsSender;
    }

    public void requestReportGeneration(ReportGenerationRequestDto reportGenerationRequest,
                                        Map<String, Value> data,
                                        String orgId) {
        ReportRequest request = to(reportGenerationRequest, data, orgId);
        ReportGenerationStatus status = reportsClient.generateReport(request);
        reportStatusRepository.create(status);
        wsSender.send("com.soitio.reports.service.ws.WebsocketServer",
                new WsMessage<>("event.reports",
                        new ConcreteJobContent(EventStatus.REQUESTED, status.getJobId())));
    }

    private ReportRequest to(ReportGenerationRequestDto reportGenerationRequest,
                             Map<String, Value> data,
                             String orgId) {
        return ReportRequest.newBuilder()
                .setName(reportGenerationRequest.getName())
                .setTemplate(reportGenerationRequest.getTemplate())
                .setOrgId(orgId)
                .putAllData(data)
                .build();
    }

}
