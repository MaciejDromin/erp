package pl.mlisowski.proxy.finances.plannedexpenses;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MultivaluedMap;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.RestQuery;

@Path("/finances/planned-expenses")
@RegisterRestClient(baseUri = "stork://finances")
public interface PlannedExpensesClient {

    @GET
    Object getAll(@RestQuery MultivaluedMap<String, String> queryParams);

    @POST
    void create(Object creation);

    @PATCH
    @Path("/{plannedExpenseId}/abandon")
    void abandon(@PathParam("plannedExpenseId") String plannedExpenseId);

    @PATCH
    @Path("/{plannedExpenseId}/complete")
    void complete(@PathParam("plannedExpenseId") String plannedExpenseId, Object completion);
}
