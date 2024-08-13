package com.soitio.finances.objectvalues.domain.dto;

import lombok.Builder;
import lombok.Value;
import com.soitio.finances.common.dto.AmountDto;

@Value
@Builder
public class TotalObjectsValueDto {

    int objectsCount;
    AmountDto totalAmount;

}
