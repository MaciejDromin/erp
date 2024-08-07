package pl.mlisowski.finances.objectvalues.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import pl.mlisowski.finances.common.persistence.BaseEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
public class ObjectValue extends BaseEntity {

    private String objectId;

    @Column(precision = 19, scale = 3)
    private BigDecimal amount;

    private String currency;

    @Enumerated
    private ObjectType objectType;

    public Money getAmount() {
        return Money.of(CurrencyUnit.of(this.currency), this.amount, RoundingMode.DOWN);
    }

}
