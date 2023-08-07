package pl.mlisowski.proxy.planner.labours;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/proxy/planner/labours")
public class LaboursController {

    @RestClient
    private LaboursClient laboursClient;

    @GET
    public Object getAll(@Context UriInfo uriInfo) {
        return laboursClient.getAll(uriInfo.getQueryParameters());
    }

    @POST
    public void createLabour(Object creationRequest) {
        laboursClient.create(creationRequest);
    }

}
