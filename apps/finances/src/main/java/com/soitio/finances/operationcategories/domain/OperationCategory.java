package com.soitio.finances.operationcategories.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import com.soitio.finances.common.persistence.BaseEntity;
import com.soitio.finances.moneyoperation.domain.MoneyOperationType;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
public class OperationCategory extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private MoneyOperationType operationType;

    private String operationName;

}
