package com.soitio.inventory.maintenance.web;

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
import com.soitio.inventory.maintenance.application.MaintenanceRepository;
import com.soitio.inventory.maintenance.domain.dto.MaintenanceCreationDto;
import com.soitio.inventory.maintenance.domain.dto.MaintenanceForListDto;

import java.util.Set;

@Path("/maintenance")
@RequiredArgsConstructor
public class MaintenanceController {

    private final MaintenanceRepository maintenanceRepository;

    @POST
    public void createMaintenanteRecord(MaintenanceCreationDto maintenanceCreation) {
        maintenanceRepository.create(maintenanceCreation);
    }

    @GET
    public PageDto<MaintenanceForListDto> getMaintenance(@Context UriInfo uriInfo) {
        return maintenanceRepository.getForList(uriInfo);
    }

    @DELETE
    public DependencyCheckResponse delete(Set<String> ids) {
        return maintenanceRepository.delete(Dependent.INVENTORY_MAINTENANCE, ids);
    }

}
