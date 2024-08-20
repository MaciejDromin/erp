package com.soitio.widgets.finances.web;

import com.soitio.widgets.finances.application.FiltersDataService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import lombok.RequiredArgsConstructor;
import java.time.Month;
import java.util.List;

@Path("/filters")
@RequiredArgsConstructor
public class FiltersDataController {

    private final FiltersDataService filtersDataService;

    @GET
    @Path("/balance/year")
    public List<Integer> getBalanceYears() {
        return filtersDataService.getBalanceYears();
    }

    @GET
    @Path("/balance/month")
    public List<Month> getBalanceMonths(@QueryParam("year") int year) {
        return filtersDataService.getBalanceMonths(year);
    }

}
