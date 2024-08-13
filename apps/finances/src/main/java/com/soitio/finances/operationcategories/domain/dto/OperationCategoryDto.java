package com.soitio.finances.operationcategories.domain.dto;

import lombok.Builder;
import lombok.Value;
import com.soitio.finances.moneyoperation.domain.MoneyOperationType;

@Value
@Builder
public class OperationCategoryDto {

    String uuid;
    MoneyOperationType operationType;
    String operationName;

}
