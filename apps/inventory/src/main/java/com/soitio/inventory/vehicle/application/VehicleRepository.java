package com.soitio.inventory.vehicle.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soitio.commons.dependency.DependencyCheckRequester;
import com.soitio.commons.models.commons.MergePatch;
import com.soitio.inventory.dependency.AbstractDependencyCheckRepo;
import com.soitio.inventory.vehicle.domain.dto.VehicleDto;
import com.soitio.inventory.vehicle.domain.enums.BodyStyle;
import com.soitio.inventory.vehicle.domain.enums.DriveTrain;
import com.soitio.inventory.vehicle.domain.enums.FuelType;
import com.soitio.inventory.vehicle.domain.enums.Make;
import com.soitio.inventory.vehicle.domain.enums.Transmission;
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
public class VehicleRepository extends AbstractDependencyCheckRepo<Vehicle> {

    private static final Integer DEFAULT_PAGE_SIZE = 20;

    public VehicleRepository(ObjectMapper mapper,
                             DependencyCheckRequester dependencyCheckRequester) {
        super(mapper, dependencyCheckRequester);
    }

    public PageDto<VehicleForListDto> getForList(UriInfo uriInfo, String orgId) {
        var params = uriInfo.getQueryParameters();
        var requestedPage = params.getFirst("page");
        var pageNum = requestedPage == null ? 1 : Integer.parseInt(requestedPage);
        var objectIdsString = params.getFirst("objectIds");
        PanacheQuery<Vehicle> vehicles;
        if (objectIdsString == null) vehicles = findAllByOrgId(orgId);
        else {
            List<String> objectIds = Arrays.asList(objectIdsString.split(","));
            if (objectIds.isEmpty()) vehicles = findAllByOrgId(orgId);
            else {
                vehicles = findAllByIdsNotInAndOrgId(objectIds, orgId);
            }
        }
        var propertyList = vehicles.page(pageNum, DEFAULT_PAGE_SIZE).list();
        return PageDto.of(propertyList.stream()
                .map(this::to)
                .toList(), vehicles.pageCount());
    }

    public void create(VehicleCreationDto vehicleCreation, String orgId) {
        persist(from(vehicleCreation, orgId));
    }

    public Map<String, Integer> findCountByObjectsIds(List<String> vehicleIds, String orgId) { // What happened here?
        return vehicleIds.stream()
                .collect(Collectors.toMap(id -> id, id -> 1));
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

    private Vehicle from(VehicleCreationDto vehicleCreation, String orgId) {
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
                .orgId(orgId)
                .build();
    }

    public Map<String, String> findAllItemNamesByIds(List<String> itemIds, String orgId) {
        Map<String, String> ret = new HashMap<>();
        var items = listAllByIdsInAndOrgId(itemIds, orgId);
        items.forEach(item -> ret.put(item.getId().toString(), item.getName()));
        return ret;
    }

    @Override
    protected Vehicle mapToEntity(MergePatch object) {
        var fields = object.getObjectValue();
        return Vehicle.builder()
                .id(new ObjectId(fields.get("id").getStrValue()))
                .name(fields.get("name").getStrValue())
                .year(fields.get("year").getIntValue())
                .odometer(fields.get("odometer").getIntValue())
                .bodyStyle(BodyStyle.getEnum(fields.get("bodyStyle").getStrValue()))
                .make(Make.getEnum(fields.get("make").getStrValue()))
                .model(fields.get("model").getStrValue())
                .fuelType(FuelType.getEnum(fields.get("fuelType").getStrValue()))
                .driveTrain(DriveTrain.getEnum(fields.get("driveTrain").getStrValue()))
                .transmission(Transmission.getEnum(fields.get("transmission").getStrValue()))
                .engineType(fields.get("engineType").getStrValue())
                .vin(fields.get("vin").getStrValue())
                .registrationPlate(fields.get("registrationPlate").getStrValue())
                .build();
    }

    public VehicleDto getVehicle(String id, String orgId) {
        return toDto(findByIdAndOrgId(id, orgId));
    }

    private VehicleDto toDto(Vehicle vehicle) {
        return VehicleDto.builder()
                .id(vehicle.getId().toString())
                .name(vehicle.getName())
                .year(vehicle.getYear())
                .odometer(vehicle.getOdometer())
                .bodyStyle(vehicle.getBodyStyle())
                .make(vehicle.getMake())
                .model(vehicle.getModel())
                .fuelType(vehicle.getFuelType())
                .driveTrain(vehicle.getDriveTrain())
                .transmission(vehicle.getTransmission())
                .engineType(vehicle.getEngineType())
                .vin(vehicle.getVin())
                .registrationPlate(vehicle.getRegistrationPlate())
                .build();
    }
}
