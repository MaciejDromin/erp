package com.soitio.widgets.finances.web;

import com.soitio.widgets.finances.application.FiltersDataService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
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
    public List<Integer> getBalanceYears(@HeaderParam("X-OrgId") String orgId) {
        return filtersDataService.getBalanceYears(orgId);
    }

    @GET
    @Path("/balance/month")
    public List<Month> getBalanceMonths(@QueryParam("year") int year, @HeaderParam("X-OrgId") String orgId) {
        return filtersDataService.getBalanceMonths(year, orgId);
    }

}
