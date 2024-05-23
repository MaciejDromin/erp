package pl.mlisowski.finances.plannedexpenses.domain.dto;

import java.time.Month;
import lombok.Builder;
import lombok.Value;
import pl.mlisowski.finances.common.dto.AmountDto;
import pl.mlisowski.finances.moneyoperation.domain.MoneyOperationType;

@Value
@Builder
public class PlannedExpensesCreationDto {

    AmountDto plannedAmount;
    String operationDescription;
    MoneyOperationType operationType;
    int plannedYear;
    Month plannedMonth;
    String operationCategoryId;

}
