package pl.mlisowski.proxy.planner.investments;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/proxy/planner/investments")
public class InvestmentsController {

    @RestClient
    private InvestmentsClient investmentsClient;

    @GET
    public Object getAll(@Context UriInfo uriInfo) {
        return investmentsClient.getAll(uriInfo.getQueryParameters());
    }

    @POST
    public void createMaterial(Object creationRequest) {
        investmentsClient.create(creationRequest);
    }

}
