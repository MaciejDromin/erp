package com.soitio.finances.moneyoperation.domain.dto;

import com.soitio.finances.common.dto.AmountDto;
import com.soitio.finances.moneyoperation.domain.MoneyOperationType;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MoneyOperationCreationDto {

    AmountDto amount;
    String operationDescription;
    MoneyOperationType operationType;
    String operationCategoryId;

}
