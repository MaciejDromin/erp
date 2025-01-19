package com.soitio.inventory.maintenance.domain;

import com.soitio.commons.dependency.Dependencies;
import com.soitio.inventory.commons.BaseEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import com.soitio.inventory.maintenance.domain.dto.PartQuantity;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@MongoEntity(collection = "MaintenanceRecord")
@ToString
@Dependencies(dependent = "MaintenanceRecord", dependencies = {"inventory.contractor"})
public class MaintenanceRecord extends BaseEntity {

    private LocalDate date;
    private int odometer;
    private List<PartQuantity> parts;
    private ObjectId contractorId;

}
