package pl.mlisowski.finances.plannedexpenses.domain.dto;

import lombok.Builder;
import lombok.Value;
import pl.mlisowski.finances.common.dto.AmountDto;
import pl.mlisowski.finances.moneyoperation.domain.MoneyOperationType;
import java.time.Month;

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
