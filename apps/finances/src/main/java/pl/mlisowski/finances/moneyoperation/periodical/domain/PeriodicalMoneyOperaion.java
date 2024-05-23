package pl.mlisowski.finances.moneyoperation.periodical.domain;

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
import pl.mlisowski.finances.common.persistence.BaseEntity;
import pl.mlisowski.finances.moneyoperation.domain.MoneyOperationType;
import pl.mlisowski.finances.operationcategories.domain.OperationCategory;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@Table(indexes = {
    @Index(name = "nextApplicableMonth", columnList = "nextApplicableMonth")
})
public class PeriodicalMoneyOperaion extends BaseEntity {

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
    @JoinColumn(name = "operation_uuid")
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
