package com.soitio.finances.moneyoperation.periodical.domain.dto;

import com.soitio.commons.models.dto.finances.AmountDto;
import com.soitio.commons.models.dto.finances.MoneyOperationType;
import com.soitio.commons.models.dto.finances.OperationCategoryDto;
import java.time.Month;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PeriodicalMoneyOperationDto {

    String id;
    String operationDescription;
    AmountDto amount;
    int repetitionPeriod;
    MoneyOperationType operationType;
    Month nextApplicableMonth;
    OperationCategoryDto operationCategory;

}
