package com.soitio.inventory.vehicle.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.soitio.commons.dependency.model.DependencyCheckResponse;
import com.soitio.commons.dependency.model.Dependent;
import com.soitio.inventory.vehicle.domain.dto.VehicleDto;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
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
    public PageDto<VehicleForListDto> getVehicles(@Context UriInfo uriInfo, @HeaderParam("X-OrgId") String orgId) {
        return vehicleRepository.getForList(uriInfo, orgId);
    }

    @GET
    @Path("/{vehicleId}")
    public VehicleDto getVehicle(@PathParam("vehicleId") String id, @HeaderParam("X-OrgId") String orgId) {
        return vehicleRepository.getVehicle(id, orgId);
    }

    @POST
    public void createVehicle(VehicleCreationDto vehicleCreation, @HeaderParam("X-OrgId") String orgId) {
        vehicleRepository.create(vehicleCreation, orgId);
    }

    @POST
    @Path("/object-count")
    public Map<String, Integer> objectCount(ObjectIdsDto objectIds, @HeaderParam("X-OrgId") String orgId) {
        return vehicleRepository.findCountByObjectsIds(objectIds.getItemIds(), orgId);
    }

    @POST
    @Path("/object-names")
    public Map<String, String> objectNames(ObjectIdsDto objectNames, @HeaderParam("X-OrgId") String orgId) {
        return vehicleRepository.findAllItemNamesByIds(objectNames.getItemIds(), orgId);
    }

    @DELETE
    public DependencyCheckResponse delete(Set<String> ids, @HeaderParam("X-OrgId") String orgId) {
        return vehicleRepository.delete(Dependent.INVENTORY_VEHICLE, ids, orgId);
    }

    @PATCH
    @Path("/{vehicleId}")
    public void updateSingleVehicle(@PathParam("vehicleId") String vehicleId,
                                    JsonNode node,
                                    @HeaderParam("X-OrgId") String orgId) {
        vehicleRepository.update(Dependent.INVENTORY_VEHICLE, vehicleId, node, orgId);
    }

}
