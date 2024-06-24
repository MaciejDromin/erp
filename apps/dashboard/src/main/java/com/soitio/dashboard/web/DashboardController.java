package com.soitio.dashboard.web;

import com.soitio.dashboard.application.DashboardRepository;
import com.soitio.dashboard.domain.dto.DashboardCreationDto;
import com.soitio.dashboard.domain.dto.DashboardDto;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
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

    @POST
    public void createDashboard(DashboardCreationDto dashboardCreation) {
        dashboardRepository.createDashboard(dashboardCreation);
    }

    @PUT
    @Path("/{dashboardId}/default")
    public void setDashboardDefault(@PathParam("dashboardId") String dashboardId) {
        dashboardRepository.setDashboardDefault(dashboardId);
    }

}
