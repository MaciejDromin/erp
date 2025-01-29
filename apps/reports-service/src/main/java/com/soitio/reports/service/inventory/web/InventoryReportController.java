package com.soitio.reports.service.inventory.web;

import com.soitio.reports.service.ReportRequesterService;
import com.soitio.reports.service.data.ValueDataMapper;
import com.soitio.reports.service.domain.InitialReportStatusDto;
import com.soitio.reports.service.domain.ReportGenerationRequestDto;
import com.soitio.reports.service.inventory.InventoryDataCollectorService;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import lombok.extern.slf4j.Slf4j;

@Path("/reports/inventory")
@Slf4j
public class InventoryReportController {

    private final ReportRequesterService reportRequesterService;
    private final InventoryDataCollectorService collectorService;

    public InventoryReportController(ReportRequesterService reportRequesterService,
                                     InventoryDataCollectorService inventoryDataCollectorService) {
        this.reportRequesterService = reportRequesterService;
        this.collectorService = inventoryDataCollectorService;
    }

    @POST
    public InitialReportStatusDto requestReportGeneration(ReportGenerationRequestDto reportGenerationRequest,
                                                          @HeaderParam("X-OrgId") String orgId) {
        var data = collectorService.getAllItems(orgId);
        log.info("Fetched {} items", data.size());
        return reportRequesterService.requestReportGeneration(reportGenerationRequest, ValueDataMapper.mapToValueMap(data), orgId);
    }

}
