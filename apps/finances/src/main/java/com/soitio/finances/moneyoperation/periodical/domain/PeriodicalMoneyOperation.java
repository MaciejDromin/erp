package com.soitio.finances.moneyoperation.periodical.domain;

import com.soitio.commons.dependency.Dependencies;
import com.soitio.finances.common.persistence.BaseEntity;
import com.soitio.finances.moneyoperation.domain.MoneyOperationType;
import com.soitio.finances.operationcategories.domain.OperationCategory;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Month;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@Table(indexes = {
    @Index(name = "nextApplicableMonth", columnList = "nextApplicableMonth")
})
@Dependencies(dependent = "PeriodicalMoneyOperation", dependencies = {"finances.category"})
public class PeriodicalMoneyOperation extends BaseEntity {

    private BigDecimal amount;

    private String currency;
    private String operationDescription;

    @Min(1L)
    @Max(12L)
    private int repetitionPeriod;

    @Enumerated(EnumType.STRING)
    private MoneyOperationType operationType;

    @Enumerated(EnumType.STRING)
    private Month nextApplicableMonth;

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
