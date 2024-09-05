package com.soitio.finances.objectvalues.domain.dto;

import com.soitio.finances.common.dto.AmountDto;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ObjectValueDto {

    String uuid;
    String objectId;
    AmountDto amount;

}
