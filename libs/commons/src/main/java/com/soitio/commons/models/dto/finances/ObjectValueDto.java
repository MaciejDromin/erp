package com.soitio.commons.models.dto.finances;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class ObjectValueDto {

    String uuid;
    String objectId;
    AmountDto amount;
    ObjectType objectType;

}