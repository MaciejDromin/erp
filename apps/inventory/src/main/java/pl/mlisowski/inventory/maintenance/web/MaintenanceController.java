package pl.mlisowski.inventory.maintenance.web;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import lombok.RequiredArgsConstructor;
import pl.mlisowski.inventory.common.PageDto;
import pl.mlisowski.inventory.maintenance.application.MaintenanceRepository;
import pl.mlisowski.inventory.maintenance.domain.dto.MaintenanceCreationDto;
import pl.mlisowski.inventory.maintenance.domain.dto.MaintenanceForListDto;

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

}
