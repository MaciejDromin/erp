package com.soitio.widgets.finances.web;

import com.soitio.widgets.common.domain.data.WidgetData;
import com.soitio.widgets.finances.application.WidgetService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import java.time.Month;
import lombok.RequiredArgsConstructor;

@Path("/widgets")
@RequiredArgsConstructor
public class WidgetsController {

    private final WidgetService widgetService;

    @GET
    @Path("/total-net-worth")
    public WidgetData getNetWorth(@HeaderParam("X-OrgId") String orgId) {
        return widgetService.calculateTotalNetWorth(orgId);
    }

    @GET
    @Path("/monthly-balance")
    public WidgetData getMonthlyBalance(@HeaderParam("X-OrgId") String orgId) {
        return widgetService.calculateMonthlyBalance(orgId);
    }

    @GET
    @Path("/monthly-balance-diff")
    public WidgetData getMonthlyBalanceDiff(@QueryParam("year") int year,
                                            @QueryParam("month") Month month,
                                            @HeaderParam("X-OrgId") String orgId) {
        return widgetService.calculateMonthlyBalanceDiff(year, month, orgId);
    }

    @GET
    @Path("/most-expensive-item-per-category")
    public WidgetData getMostExpensiveItemPerCat(@HeaderParam("X-OrgId") String orgId) {
        return widgetService.getMostExpensiveItemPerCat(orgId);
    }

    @GET
    @Path("/value-per-category")
    public WidgetData getValuePerCategory(@HeaderParam("X-OrgId") String orgId) {
        return widgetService.getValuePerCategory(orgId);
    }

    @GET
    @Path("/remaining-planned-expenses")
    public WidgetData getRemainingPlannedExpenses(@HeaderParam("X-OrgId") String orgId) {
        return widgetService.getRemainingPlannedExpenses(orgId);
    }

}