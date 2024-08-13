package com.soitio.inventory.maintenance.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.bson.types.ObjectId;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@Getter
@ToString
public class MaintenanceForListDto {

    private ObjectId id;
    private LocalDate date;
    private int odometer;

}
