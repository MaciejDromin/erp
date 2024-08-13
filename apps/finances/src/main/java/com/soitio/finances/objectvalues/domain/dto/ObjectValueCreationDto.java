package com.soitio.finances.objectvalues.domain.dto;

import lombok.Builder;
import lombok.Value;
import com.soitio.finances.objectvalues.domain.ObjectType;

@Value
@Builder
public class ObjectValueCreationDto {

    String objectId;
    String amount;
    String currencyCode;
    ObjectType objectType;

}
