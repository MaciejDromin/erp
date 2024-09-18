package com.soitio.finances.objectvalues.domain.dto;

import com.soitio.commons.models.dto.finances.AmountDto;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TotalObjectsValueDto {

    int objectsCount;
    AmountDto totalAmount;

}
