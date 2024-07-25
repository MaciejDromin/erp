package pl.mlisowski.inventory.vehicle.web;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import lombok.RequiredArgsConstructor;
import pl.mlisowski.inventory.common.PageDto;
import pl.mlisowski.inventory.item.domain.dto.ObjectIdsDto;
import pl.mlisowski.inventory.vehicle.application.VehicleRepository;
import pl.mlisowski.inventory.vehicle.domain.dto.VehicleCreationDto;
import pl.mlisowski.inventory.vehicle.domain.dto.VehicleForListDto;
import java.util.Map;

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

}
