package pl.mlisowski.proxy.finances.plannedexpenses;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Slf4j
@Path("/proxy/finances/planned-expenses")
public class PlannedExpensesController {

    @RestClient
    private PlannedExpensesClient plannedExpensesClient;

    @GET
    public Object getAll(@Context UriInfo uriInfo) {
        return plannedExpensesClient.getAll(uriInfo.getQueryParameters());
    }

    @POST
    public void create(Object creation) {
        plannedExpensesClient.create(creation);
    }

    @PATCH
    @Path("/{plannedExpenseId}/abandon")
    public void abandon(@PathParam("plannedExpenseId") String plannedExpenseId) {
        log.info("ML --- plannedExpenseId {}", plannedExpenseId);
        plannedExpensesClient.abandon(plannedExpenseId);
    }

    @PATCH
    @Path("/{plannedExpenseId}/complete")
    public void complete(@PathParam("plannedExpenseId") String plannedExpenseId, Object completion) {
        plannedExpensesClient.complete(plannedExpenseId, completion);
    }

}
