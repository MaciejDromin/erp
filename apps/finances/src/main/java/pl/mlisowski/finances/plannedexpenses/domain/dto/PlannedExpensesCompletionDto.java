package pl.mlisowski.finances.plannedexpenses.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.mlisowski.finances.common.dto.AmountDto;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PlannedExpensesCompletionDto {

    private AmountDto actualAmount;

}
