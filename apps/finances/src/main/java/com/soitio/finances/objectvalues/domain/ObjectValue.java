package com.soitio.finances.objectvalues.domain;

import com.soitio.commons.dependency.Dependencies;
import com.soitio.finances.common.persistence.BaseEntity;
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

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Dependencies(dependent = "ObjectValue", dependencies = {"inventory.item"})
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
