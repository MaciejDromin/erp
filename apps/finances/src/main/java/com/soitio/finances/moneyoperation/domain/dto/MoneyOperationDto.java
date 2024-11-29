package com.soitio.finances.moneyoperation.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.soitio.commons.models.dto.finances.AmountDto;
import com.soitio.finances.moneyoperation.domain.MoneyOperationType;
import com.soitio.finances.operationcategories.domain.dto.OperationCategoryDto;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MoneyOperationDto {

    String id;
    AmountDto amount;
    String operationDescription;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime effectiveDate;
    MoneyOperationType operationType;
    OperationCategoryDto operationCategory;

}
