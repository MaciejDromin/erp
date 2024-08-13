package com.soitio.planner.material.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class MaterialWithQuantityDto {

    private MaterialDto material;
    private double quantity;

}
