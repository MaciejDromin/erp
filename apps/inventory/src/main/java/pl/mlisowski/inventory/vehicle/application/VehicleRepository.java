package pl.mlisowski.inventory.vehicle.application;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import io.quarkus.mongodb.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.UriInfo;
import org.bson.types.ObjectId;
import pl.mlisowski.inventory.common.PageDto;
import pl.mlisowski.inventory.vehicle.domain.Vehicle;
import pl.mlisowski.inventory.vehicle.domain.dto.VehicleCreationDto;
import pl.mlisowski.inventory.vehicle.domain.dto.VehicleForListDto;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class VehicleRepository implements PanacheMongoRepository<Vehicle> {

    private static final Integer DEFAULT_PAGE_SIZE = 20;

    public PageDto<VehicleForListDto> getForList(UriInfo uriInfo) {
        var params = uriInfo.getQueryParameters();
        var requestedPage = params.getFirst("page");
        var pageNum = requestedPage == null ? 1 : Integer.parseInt(requestedPage);
        var objectIdsString = params.getFirst("objectIds");
        PanacheQuery<Vehicle> vehicles;
        if (objectIdsString == null) vehicles = findAll();
        else {
            List<String> objectIds = Arrays.asList(objectIdsString.split(","));
            if (objectIds.isEmpty()) vehicles = findAll();
            else {
                vehicles = findAllByIdsNotIn(objectIds.stream()
                        .map(ObjectId::new)
                        .collect(Collectors.toSet()));
            }
        }
        var propertyList = vehicles.page(pageNum, DEFAULT_PAGE_SIZE).list();
        return PageDto.of(propertyList.stream()
                .map(this::to)
                .toList(), vehicles.pageCount());
    }

    public void create(VehicleCreationDto vehicleCreation) {
        persist(from(vehicleCreation));
    }

    public Map<String, Integer> findCountByObjectsIds(List<String> vehicleIds) {
        return vehicleIds.stream()
                .collect(Collectors.toMap(id -> id, id -> 1));
    }

    public PanacheQuery<Vehicle> findAllByIdsNotIn(Set<ObjectId> itemIds) {
        return find("{_id: { $nin: [?1]}}", itemIds);
    }

    private VehicleForListDto to(Vehicle vehicle) {
        return VehicleForListDto.builder()
                .id(vehicle.getId())
                .name(vehicle.getName())
                .year(vehicle.getYear())
                .make(vehicle.getMake())
                .model(vehicle.getModel())
                .odometer(vehicle.getOdometer())
                .build();
    }

    private Vehicle from(VehicleCreationDto vehicleCreation) {
        return Vehicle.builder()
                .name(vehicleCreation.getName())
                .year(vehicleCreation.getYear())
                .odometer(vehicleCreation.getOdometer())
                .bodyStyle(vehicleCreation.getBodyStyle())
                .make(vehicleCreation.getMake())
                .model(vehicleCreation.getModel())
                .fuelType(vehicleCreation.getFuelType())
                .driveTrain(vehicleCreation.getDriveTrain())
                .transmission(vehicleCreation.getTransmission())
                .engineType(vehicleCreation.getEngineType())
                .vin(vehicleCreation.getVin())
                .build();
    }
    
}
