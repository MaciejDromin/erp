package pl.mlisowski.planner.investment.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import pl.mlisowski.planner.common.dto.AmountDto;

@Builder
@AllArgsConstructor
@Getter
public class InvestmentShortDto {

    private String id;
    private String name;
    private int totalEstimatedTime;
    private AmountDto totalCost;

}
