package pl.mlisowski.inventory.maintenance.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.List;

@Builder
@AllArgsConstructor
@Getter
@ToString
public class MaintenanceCreationDto {

    private LocalDate date;
    private int odometer;
    private List<PartQuantity> parts;
    private ObjectId contractorId;

}
