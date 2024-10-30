package com.soitio.inventory.vehicle.application;

import com.soitio.commons.dependency.DependencyCheckRequester;
import com.soitio.inventory.dependency.AbstractDependencyCheckRepo;
import com.soitio.inventory.vehicle.domain.dto.VehicleDto;
import io.quarkus.mongodb.panache.PanacheQuery;
import jakarta.inject.Singleton;
import jakarta.ws.rs.core.UriInfo;
import org.bson.types.ObjectId;
import com.soitio.commons.models.dto.PageDto;
import com.soitio.inventory.vehicle.domain.Vehicle;
import com.soitio.inventory.vehicle.domain.dto.VehicleCreationDto;
import com.soitio.inventory.vehicle.domain.dto.VehicleForListDto;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class VehicleRepository extends AbstractDependencyCheckRepo<Vehicle, VehicleDto> {

    private static final Integer DEFAULT_PAGE_SIZE = 20;

    public VehicleRepository(DependencyCheckRequester dependencyCheckRequester) {
        super(dependencyCheckRequester);
    }

    @Override
    public void updateOne(String id, VehicleDto object) {
        // TODO: Finish
    }

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
                .registrationPlate(vehicle.getRegistrationPlate())
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
                .registrationPlate(vehicleCreation.getRegistrationPlate())
                .build();
    }

    public Map<String, String> findAllItemNamesByIds(List<String> itemIds) {
        Map<String, String> ret = new HashMap<>();
        var items = findAllByIdsIn(itemIds.stream()
                .map(ObjectId::new)
                .collect(Collectors.toSet()));
        items.forEach(item -> ret.put(item.getId().toString(), item.getName()));
        return ret;
    }

    public Set<Vehicle> findAllByIdsIn(Set<ObjectId> itemIds) {
        return new HashSet<>(list("_id in ?1", itemIds));
    }

}
