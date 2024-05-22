package pl.mlisowski.finances.receipt.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import jakarta.persistence.OneToMany;
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
public class Purchase extends BaseEntity {

    private BigDecimal amount;
    private String currency;
    private PurchaseAnalytics analytics;
    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseItem> purchaseItems;

    public Money getAmount() {
        return Money.of(CurrencyUnit.of(this.currency), this.amount, RoundingMode.DOWN);
    }

}
