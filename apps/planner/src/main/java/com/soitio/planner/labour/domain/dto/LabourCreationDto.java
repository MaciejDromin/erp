package com.soitio.planner.labour.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import com.soitio.planner.common.dto.AmountDto;
import com.soitio.planner.common.model.QuantityUnit;

@Builder
@AllArgsConstructor
@Getter
public class LabourCreationDto {

    private String name;
    private AmountDto rateAmount;
    private String contractorName;
    private String contractorContact;
    private QuantityUnit unit;

}
