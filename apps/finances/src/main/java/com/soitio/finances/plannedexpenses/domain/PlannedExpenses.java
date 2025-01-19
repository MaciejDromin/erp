package com.soitio.finances.plannedexpenses.domain;

import com.soitio.commons.dependency.Dependencies;
import com.soitio.commons.models.dto.finances.MoneyOperationType;
import com.soitio.commons.models.dto.finances.PlannedExpensesStatus;
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
import lombok.Builder;
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
@Dependencies(dependent = "PlannedExpenses", dependencies = {"finances.category"})
@Table(indexes = {
    @Index(name = "org_id", columnList = "id, orgId", unique = true)
})
public class PlannedExpenses extends BaseEntity {

    private BigDecimal plannedAmount;
    private BigDecimal actualAmount;

    private String currency;
    private String operationDescription;

    @Enumerated(EnumType.STRING)
    private MoneyOperationType operationType;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private PlannedExpensesStatus plannedExpensesStatus = PlannedExpensesStatus.PLANNED;

    private LocalDateTime finalizedDate;
    private int plannedYear;
    private Month plannedMonth;

    @ManyToOne
    @JoinColumn(name = "operation_id")
    private OperationCategory operationCategory;

    public OperationCategory getOperationCategory() {
        return operationCategory;
    }

    public void setOperationCategory(OperationCategory operationCategory) {
        this.operationCategory = operationCategory;
    }

    public Money getPlannedAmount() {
        return Money.of(CurrencyUnit.of(this.currency), this.plannedAmount, RoundingMode.DOWN);
    }

    public Money getActualAmount() {
        if (this.actualAmount == null) {
            return Money.zero(CurrencyUnit.of(this.currency));
        }
        return Money.of(CurrencyUnit.of(this.currency), this.actualAmount, RoundingMode.DOWN);
    }

    public boolean isFinalized() {
        return this.plannedExpensesStatus != PlannedExpensesStatus.PLANNED;
    }

}
