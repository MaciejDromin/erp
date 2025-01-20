package com.soitio.widgets.finances.inventory.client;

import com.soitio.commons.models.dto.PageDto;
import com.soitio.commons.models.dto.inventory.item.InventoryItemDto;
import com.soitio.widgets.finances.inventory.domain.ObjectIdsDto;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import java.util.Map;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.RestQuery;

@Path("/")
@RegisterRestClient
public interface InventoryClient {

    @POST
    @Path("/items/object-count")
    Map<String, Integer> itemCount(@HeaderParam("X-OrgId") String orgId, ObjectIdsDto objectIds);

    @POST
    @Path("/properties/object-count")
    Map<String, Integer> propertyCount(@HeaderParam("X-OrgId") String orgId, ObjectIdsDto objectIds);

    @POST
    @Path("/vehicles/object-count")
    Map<String, Integer> vehicleCount(@HeaderParam("X-OrgId") String orgId, ObjectIdsDto objectIds);

    @GET
    @Path("/items")
    PageDto<InventoryItemDto> getAllItems(@HeaderParam("X-OrgId") String orgId, @RestQuery Map<String, String> params);

}
