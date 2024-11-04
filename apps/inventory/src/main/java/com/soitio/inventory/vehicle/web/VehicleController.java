package com.soitio.inventory.vehicle.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.soitio.commons.dependency.model.DependencyCheckResponse;
import com.soitio.commons.dependency.model.Dependent;
import com.soitio.inventory.vehicle.domain.dto.VehicleDto;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
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
    public PageDto<VehicleForListDto> getVehicles(@Context UriInfo uriInfo) {
        return vehicleRepository.getForList(uriInfo);
    }

    @GET
    @Path("/{vehicleId}")
    public VehicleDto getVehicle(@PathParam("vehicleId") String id) {
        return vehicleRepository.getVehicle(id);
    }

    @POST
    public void createVehicle(VehicleCreationDto vehicleCreation) {
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
        return vehicleRepository.delete(Dependent.INVENTORY_VEHICLE, ids);
    }

    @PATCH
    @Path("/{vehicleId}")
    public void updateSingleVehicle(@PathParam("vehicleId") String vehicleId, JsonNode node) {
        vehicleRepository.update(Dependent.INVENTORY_VEHICLE, vehicleId, node);
    }

}
