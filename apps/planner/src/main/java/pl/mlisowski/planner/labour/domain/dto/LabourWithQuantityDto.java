package pl.mlisowski.planner.labour.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class LabourWithQuantityDto {

    private LabourDto labour;
    private double requiredQuantity;

}
