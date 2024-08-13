package com.soitio.planner.labour.domain;

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
@MongoEntity(collection = "Labour")
public class Labour {

    private ObjectId id;
    private String name;
    private BigDecimal rateAmount;
    private String currency;
    private LocalDateTime updatedTime;
    private String contractorName;
    private String contractorContact;
    private QuantityUnit unit;

    @BsonIgnore
    public Money getRateAmountMoney() {
        return Money.of(CurrencyUnit.of(this.currency), this.rateAmount, RoundingMode.DOWN);
    }

}
