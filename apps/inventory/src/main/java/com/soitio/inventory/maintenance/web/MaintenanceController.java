package com.soitio.inventory.maintenance.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.soitio.commons.dependency.model.DependencyCheckResponse;
import com.soitio.commons.dependency.model.Dependent;
import com.soitio.inventory.maintenance.domain.dto.MaintenanceRecordDto;
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
import com.soitio.inventory.maintenance.application.MaintenanceRepository;
import com.soitio.inventory.maintenance.domain.dto.MaintenanceCreationDto;
import com.soitio.inventory.maintenance.domain.dto.MaintenanceForListDto;

import java.util.Set;

@Path("/maintenance")
@RequiredArgsConstructor
public class MaintenanceController {

    private final MaintenanceRepository maintenanceRepository;

    @POST
    public void createMaintenanteRecord(MaintenanceCreationDto maintenanceCreation, @HeaderParam("X-OrgId") String orgId) {
        maintenanceRepository.create(maintenanceCreation, orgId);
    }

    @GET
    public PageDto<MaintenanceForListDto> getMaintenance(@Context UriInfo uriInfo, @HeaderParam("X-OrgId") String orgId) {
        return maintenanceRepository.getForList(uriInfo, orgId);
    }

    @GET
    @Path("/{maintenanceId}")
    public MaintenanceRecordDto getSingleMaintenance(@PathParam("maintenanceId") String id,
                                                     @HeaderParam("X-OrgId") String orgId) {
        return maintenanceRepository.getMaintenance(id, orgId);
    }

    @DELETE
    public DependencyCheckResponse delete(Set<String> ids, @HeaderParam("X-OrgId") String orgId) {
        return maintenanceRepository.delete(Dependent.INVENTORY_MAINTENANCE, ids, orgId);
    }

    @PATCH
    @Path("/{maintenanceId}")
    public void updateSingleMaintenance(@PathParam("maintenanceId") String maintenanceId,
                                        JsonNode node,
                                        @HeaderParam("X-OrgId") String orgId) {
        maintenanceRepository.update(Dependent.INVENTORY_MAINTENANCE, maintenanceId, node, orgId);
    }

}
