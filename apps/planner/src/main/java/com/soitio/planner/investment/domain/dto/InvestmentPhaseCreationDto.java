package com.soitio.planner.investment.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import com.soitio.planner.labour.domain.dto.LabourWithQuantityCreationDto;
import com.soitio.planner.material.domain.dto.MaterialWithQuantityCreationDto;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class InvestmentPhaseCreationDto {

    private String name;
    private int phaseTime;
    private List<InvestmentPhaseCreationDto> subPhases;
    private List<MaterialWithQuantityCreationDto> materials;
    private List<LabourWithQuantityCreationDto> labours;
    private String currency;

}
