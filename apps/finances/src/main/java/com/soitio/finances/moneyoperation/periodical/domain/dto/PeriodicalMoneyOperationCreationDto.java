package com.soitio.finances.moneyoperation.periodical.domain.dto;

import com.soitio.commons.models.dto.finances.AmountDto;
import com.soitio.finances.moneyoperation.domain.MoneyOperationType;
import java.time.Month;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PeriodicalMoneyOperationCreationDto {

    AmountDto amount;
    String operationDescription;
    MoneyOperationType operationType;
    Month nextApplicableMonth;
    int repetitionPeriod;
    String operationCategoryId;

}
