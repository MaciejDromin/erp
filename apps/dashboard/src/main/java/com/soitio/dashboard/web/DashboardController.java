package com.soitio.dashboard.web;

import com.soitio.dashboard.application.DashboardRepository;
import com.soitio.dashboard.domain.dto.DashboardCreationDto;
import com.soitio.dashboard.domain.dto.DashboardDto;
import com.soitio.dashboard.domain.dto.DashboardForSelectionDto;
import com.soitio.dashboard.widget.domain.dto.WidgetCreationDto;
import jakarta.ws.rs.GET;
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
    public List<DashboardDto> getDashboards() {
        return dashboardRepository.getDashboards();
    }

    @GET
    @Path("/{dashboardId}")
    public DashboardDto getDashboard(@PathParam("dashboardId") String dashboardId) {
        return dashboardRepository.getDashboard(dashboardId);
    }

    @GET
    @Path("/types/{type}")
    public DashboardDto getDashboardForType(@PathParam("type") String type) {
        return dashboardRepository.getDashboardForType(type);
    }

    @GET
    @Path("/types/{type}/selection")
    public List<DashboardForSelectionDto> getDashboardForSelection(@PathParam("type") String type) {
        return dashboardRepository.getDashboardForSelection(type);
    }

    @POST
    public void createDashboard(DashboardCreationDto dashboardCreation) {
        dashboardRepository.createDashboard(dashboardCreation);
    }

    @PATCH
    @Path("/{dashboardId}/default")
    public void setDashboardDefault(@PathParam("dashboardId") String dashboardId) {
        dashboardRepository.setDashboardDefault(dashboardId);
    }

    @POST
    @Path("/{dashboardId}/widgets")
    public void createWidget(@PathParam("dashboardId") String dashboardId, WidgetCreationDto widgetCreation) {
        dashboardRepository.createWidgetInDashboard(widgetCreation, dashboardId);
    }

}
