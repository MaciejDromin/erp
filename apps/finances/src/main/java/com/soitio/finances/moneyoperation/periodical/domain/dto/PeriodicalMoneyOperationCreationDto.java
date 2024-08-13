package com.soitio.finances.moneyoperation.periodical.domain.dto;

import java.time.Month;
import lombok.Builder;
import lombok.Value;
import com.soitio.finances.common.dto.AmountDto;
import com.soitio.finances.moneyoperation.domain.MoneyOperationType;

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
