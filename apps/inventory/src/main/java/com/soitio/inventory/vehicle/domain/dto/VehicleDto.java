package com.soitio.inventory.vehicle.domain.dto;

import com.soitio.inventory.vehicle.domain.enums.BodyStyle;
import com.soitio.inventory.vehicle.domain.enums.DriveTrain;
import com.soitio.inventory.vehicle.domain.enums.FuelType;
import com.soitio.inventory.vehicle.domain.enums.Make;
import com.soitio.inventory.vehicle.domain.enums.Transmission;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.bson.types.ObjectId;

@Builder
@AllArgsConstructor
@Getter
@ToString
public class VehicleDto {

    private String id;
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
