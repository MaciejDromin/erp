package com.soitio.widgets.finances.client;

import jakarta.ws.rs.GET;
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
    List<Integer> getDistinctYears();

    @GET
    @Path("/months")
    List<Month> getDistinctMonths(@QueryParam("year") int year);

}
