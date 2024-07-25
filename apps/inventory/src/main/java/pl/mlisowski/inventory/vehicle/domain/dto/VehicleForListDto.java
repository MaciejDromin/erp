package pl.mlisowski.inventory.vehicle.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.bson.types.ObjectId;
import pl.mlisowski.inventory.vehicle.domain.enums.Make;

@Builder
@AllArgsConstructor
@Getter
@ToString
public class VehicleForListDto {

    private ObjectId id;
    private String name;
    private int year;
    private Make make;
    private String model;
    private int odometer;

}
