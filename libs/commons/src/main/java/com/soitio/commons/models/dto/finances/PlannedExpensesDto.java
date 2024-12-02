package com.soitio.commons.models.dto.finances;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Value;
import java.time.LocalDateTime;
import java.time.Month;

@Value
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlannedExpensesDto {

    String id;
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
