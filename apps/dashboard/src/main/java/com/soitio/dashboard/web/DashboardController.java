package com.soitio.dashboard.web;

import com.soitio.dashboard.application.DashboardRepository;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import lombok.RequiredArgsConstructor;

@Path("/dashboards")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardRepository dashboardRepository;

    @GET
    @Path("/{dashboardId}")
    public Object getDashboard(@PathParam("dashboardId") String dashboardId) {
        return dashboardRepository.getDashboard(dashboardId);
    }

    @GET
    @Path("/types/{type}")
    public Object getDashboardForType(@PathParam("type") String type) {
        return dashboardRepository.getDashboardForType(type);
    }

}
