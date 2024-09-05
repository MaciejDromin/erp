package com.soitio.finances.operationcategories.domain.dto;

import com.soitio.finances.moneyoperation.domain.MoneyOperationType;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class OperationCategoryDto {

    String uuid;
    MoneyOperationType operationType;
    String operationName;

}
