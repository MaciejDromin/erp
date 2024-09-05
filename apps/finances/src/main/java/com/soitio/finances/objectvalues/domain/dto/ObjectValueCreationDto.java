package com.soitio.finances.objectvalues.domain.dto;

import com.soitio.finances.objectvalues.domain.ObjectType;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ObjectValueCreationDto {

    String objectId;
    String amount;
    String currencyCode;
    ObjectType objectType;

}
