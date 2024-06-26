package pl.mlisowski.finances.plannedexpenses.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.time.Month;
import lombok.Builder;
import lombok.Value;
import pl.mlisowski.finances.common.dto.AmountDto;
import pl.mlisowski.finances.moneyoperation.domain.MoneyOperationType;
import pl.mlisowski.finances.operationcategories.domain.dto.OperationCategoryDto;
import pl.mlisowski.finances.plannedexpenses.domain.PlannedExpensesStatus;

@Value
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlannedExpensesDto {

    String uuid;
    AmountDto plannedAmount;
    AmountDto actualAmount;
    String operationDescription;
    int plannedYear;
    Month plannedMonth;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime finalizedDate;
    PlannedExpensesStatus plannedExpensesStatus;
    MoneyOperationType operationType;
    OperationCategoryDto operationCategory;
    boolean isFinalized;

}
