package com.soitio.widgets.finances.client;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.time.Month;
import java.util.List;

@Path("/widgets")
@RegisterRestClient
public interface FiltersDataClient {

    @GET
    @Path("/years")
    List<Integer> getDistinctYears(@HeaderParam("X-OrgId") String orgId);

    @GET
    @Path("/months")
    List<Month> getDistinctMonths(@HeaderParam("X-OrgId") String orgId, @QueryParam("year") int year);

}
