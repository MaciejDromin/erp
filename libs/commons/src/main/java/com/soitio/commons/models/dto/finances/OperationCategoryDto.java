package com.soitio.commons.models.dto.finances;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class OperationCategoryDto {

    String id;
    MoneyOperationType operationType;
    String operationName;

}
