package pl.mlisowski.finances.plannedexpenses.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
import pl.mlisowski.finances.common.persistence.BaseEntity;
import pl.mlisowski.finances.moneyoperation.domain.MoneyOperationType;
import pl.mlisowski.finances.operationcategories.domain.OperationCategory;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
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
    @JoinColumn(name = "operation_uuid")
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
