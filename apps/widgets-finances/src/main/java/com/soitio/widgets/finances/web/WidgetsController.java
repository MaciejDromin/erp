package com.soitio.widgets.finances.web;

import com.soitio.widgets.common.domain.data.WidgetData;
import com.soitio.widgets.finances.application.WidgetService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import lombok.RequiredArgsConstructor;

import java.time.Month;

@Path("/widgets")
@RequiredArgsConstructor
public class WidgetsController {

    private final WidgetService widgetService;

    @GET
    @Path("/total-net-worth")
    public WidgetData getNetWorth() {
        return widgetService.calculateTotalNetWorth();
    }

    @GET
    @Path("/monthly-balance")
    public WidgetData getMonthlyBalance() {
        return widgetService.calculateMonthlyBalance();
    }

    @GET
    @Path("/monthly-balance-diff")
    public WidgetData getMonthlyBalanceDiff(@QueryParam("year") int year,
                                            @QueryParam("month") Month month) {
        return widgetService.calculateMonthlyBalanceDiff(year, month);
    }

    @GET
    @Path("/most-expensive-item-per-category")
    public WidgetData getMostExpensiveItemPerCat() {
        return widgetService.getMostExpensiveItemPerCat();
    }

    @GET
    @Path("/value-per-category")
    public WidgetData getValuePerCategory() {
        return widgetService.getValuePerCategory();
    }

    @GET
    @Path("/remaining-planned-expenses")
    public WidgetData getRemainingPlannedExpenses() {
        return widgetService.getRemainingPlannedExpenses();
    }

}