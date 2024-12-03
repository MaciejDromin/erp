package com.soitio.finances.operationcategories.domain.dto;

import com.soitio.commons.models.dto.finances.MoneyOperationType;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class OperationCategoryCreationDto {

    MoneyOperationType operationType;
    String operationName;

}
