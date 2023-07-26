package pl.mlisowski.finances.operationcategories.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pl.mlisowski.finances.common.persistence.BaseEntity;
import pl.mlisowski.finances.moneyoperation.domain.MoneyOperationType;

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
