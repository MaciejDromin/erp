package pl.mlisowski.inventory.vehicle.domain;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import pl.mlisowski.inventory.property.domain.enums.BodyStyle;
import pl.mlisowski.inventory.property.domain.enums.DriveTrain;
import pl.mlisowski.inventory.property.domain.enums.FuelType;
import pl.mlisowski.inventory.property.domain.enums.Make;
import pl.mlisowski.inventory.property.domain.enums.Transmission;

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

}
