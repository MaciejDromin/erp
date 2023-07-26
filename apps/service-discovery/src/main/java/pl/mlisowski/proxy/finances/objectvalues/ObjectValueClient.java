package pl.mlisowski.proxy.finances.objectvalues;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MultivaluedMap;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.RestQuery;

@Path("/finances/object-value")
@RegisterRestClient(baseUri = "stork://finances")
public interface ObjectValueClient {

    @GET
    Object getAll(@RestQuery MultivaluedMap<String, String> queryParams);

    @POST
    void create(Object creationRequest);

    @POST
    @Path("/total-value")
    Object totalValue(Object quantityPerObjectMap);

    @GET
    @Path("/object-ids")
    Object allObjectIds();
}
