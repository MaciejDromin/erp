package com.soitio.inventory.vehicle.domain;

import com.soitio.inventory.commons.BaseEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import com.soitio.inventory.vehicle.domain.enums.BodyStyle;
import com.soitio.inventory.vehicle.domain.enums.DriveTrain;
import com.soitio.inventory.vehicle.domain.enums.FuelType;
import com.soitio.inventory.vehicle.domain.enums.Make;
import com.soitio.inventory.vehicle.domain.enums.Transmission;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@MongoEntity(collection = "Vehicle")
@ToString
public class Vehicle extends BaseEntity {

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
