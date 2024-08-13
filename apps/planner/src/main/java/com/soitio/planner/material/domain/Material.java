package com.soitio.planner.material.domain;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.types.ObjectId;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import com.soitio.planner.common.model.QuantityUnit;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@MongoEntity(collection = "Material")
public class Material {

    private ObjectId id;
    private String name;
    private QuantityUnit unit;
    private BigDecimal unitAmount;
    private String currency;
    private LocalDateTime updatedTime;
    private String source;

    @BsonIgnore
    public Money getUnitAmountMoney() {
        return Money.of(CurrencyUnit.of(this.currency), this.unitAmount, RoundingMode.DOWN);
    }

}
