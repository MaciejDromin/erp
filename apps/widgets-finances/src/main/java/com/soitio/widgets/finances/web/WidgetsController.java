package com.soitio.widgets.finances.web;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Path("/widgets")
@RequiredArgsConstructor
public class WidgetsController {

    @GET
    @Path("/total-net-worth")
    public Object getNetWorth() {
        return List.of("HEY");
    }
}
