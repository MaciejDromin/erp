package com.soitio.widgets.finances.web;

import com.soitio.widgets.common.domain.data.WidgetData;
import com.soitio.widgets.finances.application.WidgetService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import lombok.RequiredArgsConstructor;

@Path("/widgets")
@RequiredArgsConstructor
public class WidgetsController {

    private final WidgetService widgetService;

    @GET
    @Path("/total-net-worth")
    public WidgetData getNetWorth() {
        return widgetService.calculateTotalNetWorth();
    }
}