package pl.mlisowski.finances.receipt.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import pl.mlisowski.finances.common.persistence.BaseEntity;
import pl.mlisowski.finances.receipt.domain.dto.ItemUnit;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
public class PurchaseItem extends BaseEntity {

    private String name;
    @Enumerated(EnumType.ORDINAL)
    private ItemUnit unit;
    private BigDecimal price;
    private BigDecimal unitPrice;
    private double quantity;
    private String currency;

    @ManyToOne
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;

    public Money getAmount() {
        return Money.of(CurrencyUnit.of(this.currency), this.price, RoundingMode.DOWN);
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }
}
