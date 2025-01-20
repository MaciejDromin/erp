package com.soitio.dashboard.web;

import com.soitio.dashboard.application.DashboardRepository;
import com.soitio.dashboard.domain.dto.DashboardCreationDto;
import com.soitio.dashboard.domain.dto.DashboardDto;
import com.soitio.dashboard.domain.dto.DashboardForSelectionDto;
import com.soitio.dashboard.widget.domain.dto.WidgetCreationDto;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Path("/dashboards")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardRepository dashboardRepository;

    @GET
    public List<DashboardDto> getDashboards(@HeaderParam("X-OrgId") String orgId) {
        return dashboardRepository.getDashboards(orgId);
    }

    @GET
    @Path("/{dashboardId}")
    public DashboardDto getDashboard(@PathParam("dashboardId") String dashboardId,
                                     @HeaderParam("X-OrgId") String orgId) {
        return dashboardRepository.getDashboard(dashboardId, orgId);
    }

    @GET
    @Path("/types/{type}")
    public DashboardDto getDashboardForType(@PathParam("type") String type,
                                            @HeaderParam("X-OrgId") String orgId) {
        return dashboardRepository.getDashboardForType(type, orgId);
    }

    @GET
    @Path("/types/{type}/selection")
    public List<DashboardForSelectionDto> getDashboardForSelection(@PathParam("type") String type,
                                                                   @HeaderParam("X-OrgId") String orgId) {
        return dashboardRepository.getDashboardForSelection(type, orgId);
    }

    @POST
    public void createDashboard(DashboardCreationDto dashboardCreation, @HeaderParam("X-OrgId") String orgId) {
        dashboardRepository.createDashboard(dashboardCreation, orgId);
    }

    @PATCH
    @Path("/{dashboardId}/default")
    public void setDashboardDefault(@PathParam("dashboardId") String dashboardId,
                                    @HeaderParam("X-OrgId") String orgId) {
        dashboardRepository.setDashboardDefault(dashboardId, orgId);
    }

    @POST
    @Path("/{dashboardId}/widgets")
    public void createWidget(@PathParam("dashboardId") String dashboardId,
                             WidgetCreationDto widgetCreation,
                             @HeaderParam("X-OrgId") String orgId) {
        dashboardRepository.createWidgetInDashboard(widgetCreation, dashboardId, orgId);
    }

    @DELETE
    @Path("/{dashboardId}/widgets/{widgetId}")
    public void deleteWidget(@PathParam("dashboardId") String dashboardId,
                             @PathParam("widgetId") String widgetId,
                             @HeaderParam("X-OrgId") String orgId) {
        dashboardRepository.deleteWidgetFromDashboard(widgetId, dashboardId, orgId);
    }

    @DELETE
    @Path("/{dashboardId}")
    public void deleteDashboard(@PathParam("dashboardId") String dashboardId,
                                @HeaderParam("X-OrgId") String orgId) {
        dashboardRepository.deleteByIdAndOrgId(dashboardId, orgId);
    }

}
