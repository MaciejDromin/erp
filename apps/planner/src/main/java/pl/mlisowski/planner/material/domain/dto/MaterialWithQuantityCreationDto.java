package pl.mlisowski.planner.material.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class MaterialWithQuantityCreationDto {

    private String materialId;
    private double requiredQuantity;

}
