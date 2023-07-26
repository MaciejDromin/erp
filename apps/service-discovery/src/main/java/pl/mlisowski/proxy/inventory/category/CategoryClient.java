package pl.mlisowski.proxy.inventory.category;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MultivaluedMap;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.RestQuery;

@Path("/inventory/categories")
@RegisterRestClient(baseUri = "stork://inventory")
public interface CategoryClient {

    @GET
    Object getAll(@RestQuery MultivaluedMap<String, String> queryParams);

    @POST
    void create(Object creationRequest);

}
