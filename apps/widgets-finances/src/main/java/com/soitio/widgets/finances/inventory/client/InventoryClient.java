package com.soitio.widgets.finances.inventory.client;

import com.soitio.commons.models.dto.PageDto;
import com.soitio.commons.models.dto.inventory.category.CategoryDto;
import com.soitio.commons.models.dto.inventory.item.InventoryItemDto;
import com.soitio.widgets.finances.inventory.domain.ObjectIdsDto;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import java.util.Map;

@Path("/")
@RegisterRestClient
public interface InventoryClient {

    @POST
    @Path("/inventory/items/object-count")
    Map<String, Integer> itemCount(ObjectIdsDto objectIds);

    @POST
    @Path("/properties/object-count")
    Map<String, Integer> propertyCount(ObjectIdsDto objectIds);

    @POST
    @Path("/vehicles/object-count")
    Map<String, Integer> vehicleCount(ObjectIdsDto objectIds);

    @GET
    @Path("/inventory/items")
    PageDto<InventoryItemDto> getAllItems(@QueryParam("page") Integer page, @QueryParam("size") Integer size);

}
