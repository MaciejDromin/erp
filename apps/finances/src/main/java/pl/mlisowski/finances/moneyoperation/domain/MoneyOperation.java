package pl.mlisowski.finances.moneyoperation.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Month;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import pl.mlisowski.finances.common.persistence.BaseEntity;
import pl.mlisowski.finances.operationcategories.domain.OperationCategory;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Table(indexes = {
    @Index(name = "period", columnList = "effectiveMonth, effectiveYear")
})
public class MoneyOperation extends BaseEntity {

    private BigDecimal amount;

    private String currency;
    private String operationDescription;

    private ZonedDateTime effectiveDate;

    @Enumerated(EnumType.STRING)
    private Month effectiveMonth;

    private int effectiveYear;

    @Enumerated(EnumType.STRING)
    private MoneyOperationType operationType;

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
