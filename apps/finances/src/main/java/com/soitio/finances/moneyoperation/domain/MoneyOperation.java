package com.soitio.finances.moneyoperation.domain;

import com.soitio.commons.dependency.Dependencies;
import com.soitio.finances.common.persistence.BaseEntity;
import com.soitio.finances.operationcategories.domain.OperationCategory;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.Month;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Table(indexes = {
    @Index(name = "period", columnList = "effectiveMonth, effectiveYear")
})
@Dependencies(dependent = "MoneyOperation", dependencies = {"finances.category"})
public class MoneyOperation extends BaseEntity {

    private BigDecimal amount;

    private String currency;
    private String operationDescription;

    private LocalDateTime effectiveDate;

    @Enumerated(EnumType.STRING)
    private Month effectiveMonth;

    private int effectiveYear;

    @Enumerated(EnumType.STRING)
    private MoneyOperationType operationType;

    @ManyToOne
    @JoinColumn(name = "operation_id")
    private OperationCategory operationCategory;

    public OperationCategory getOperationCategory() {
        return operationCategory;
    }

    public void setOperationCategory(OperationCategory operationCategory) {
        this.operationCategory = operationCategory;
    }

    public Money getAmount() {
        return Money.of(CurrencyUnit.of(this.currency), this.amount, RoundingMode.DOWN);
    }

}
