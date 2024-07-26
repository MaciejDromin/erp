package pl.mlisowski.inventory.vehicle.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import pl.mlisowski.inventory.vehicle.domain.enums.BodyStyle;
import pl.mlisowski.inventory.vehicle.domain.enums.DriveTrain;
import pl.mlisowski.inventory.vehicle.domain.enums.FuelType;
import pl.mlisowski.inventory.vehicle.domain.enums.Make;
import pl.mlisowski.inventory.vehicle.domain.enums.Transmission;

@Builder
@AllArgsConstructor
@Getter
@ToString
public class VehicleCreationDto {

    private String name;
    private int year;
    private int odometer;
    private BodyStyle bodyStyle;
    private Make make;
    private String model;
    private FuelType fuelType;
    private DriveTrain driveTrain;
    private Transmission transmission;
    private String engineType;
    private String vin;
    private String registrationPlate;

}
