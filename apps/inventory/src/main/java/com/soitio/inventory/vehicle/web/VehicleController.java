package com.soitio.inventory.vehicle.web;

import com.soitio.commons.dependency.model.DependencyCheckResponse;
import com.soitio.commons.dependency.model.Dependent;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import lombok.RequiredArgsConstructor;
import com.soitio.commons.models.dto.PageDto;
import com.soitio.inventory.item.domain.dto.ObjectIdsDto;
import com.soitio.inventory.vehicle.application.VehicleRepository;
import com.soitio.inventory.vehicle.domain.dto.VehicleCreationDto;
import com.soitio.inventory.vehicle.domain.dto.VehicleForListDto;
import java.util.Map;
import java.util.Set;

@Path("/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleRepository vehicleRepository;

    @GET
    public PageDto<VehicleForListDto> getProperties(@Context UriInfo uriInfo) {
        return vehicleRepository.getForList(uriInfo);
    }

    @POST
    public void createProperty(VehicleCreationDto vehicleCreation) {
        vehicleRepository.create(vehicleCreation);
    }

    @POST
    @Path("/object-count")
    public Map<String, Integer> objectCount(ObjectIdsDto objectIds) {
        return vehicleRepository.findCountByObjectsIds(objectIds.getItemIds());
    }

    @POST
    @Path("/object-names")
    public Map<String, String> objectNames(ObjectIdsDto objectNames) {
        return vehicleRepository.findAllItemNamesByIds(objectNames.getItemIds());
    }

    @DELETE
    public DependencyCheckResponse delete(Set<String> ids) {
        return vehicleRepository.delete(Dependent.INVENTORY_ITEM, ids);
    }

}
