package com.soitio.inventory.vehicle.domain;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import com.soitio.inventory.vehicle.domain.enums.BodyStyle;
import com.soitio.inventory.vehicle.domain.enums.DriveTrain;
import com.soitio.inventory.vehicle.domain.enums.FuelType;
import com.soitio.inventory.vehicle.domain.enums.Make;
import com.soitio.inventory.vehicle.domain.enums.Transmission;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@MongoEntity(collection = "Vehicle")
@ToString
@EqualsAndHashCode(of = "id")
public class Vehicle {

    private ObjectId id;
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
