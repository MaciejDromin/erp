package com.soitio.finances.operationcategories.domain;

import com.soitio.commons.models.dto.finances.MoneyOperationType;
import com.soitio.finances.common.persistence.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Table(indexes = {
    @Index(name = "org_id", columnList = "id, orgId", unique = true)
})
public class OperationCategory extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private MoneyOperationType operationType;

    private String operationName;

}
