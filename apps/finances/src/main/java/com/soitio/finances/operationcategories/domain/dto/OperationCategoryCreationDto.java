package com.soitio.finances.operationcategories.domain.dto;

import com.soitio.finances.moneyoperation.domain.MoneyOperationType;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class OperationCategoryCreationDto {

    MoneyOperationType operationType;
    String operationName;

}
