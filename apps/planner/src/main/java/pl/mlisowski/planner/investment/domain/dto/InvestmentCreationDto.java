package pl.mlisowski.planner.investment.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class InvestmentCreationDto {

    private String name;
    private List<InvestmentPhaseCreationDto> investmentPhases;
    private String currency;

}
