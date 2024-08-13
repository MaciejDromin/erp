package com.soitio.planner.labour.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class LabourWithQuantityCreationDto {

    private String labourId;
    private double requiredQuantity;

}
