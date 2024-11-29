package com.soitio.finances.currency.domain;

import java.math.BigDecimal;

public interface CurrencyRateProj {

    String getId();

    String getCode();

    BigDecimal getRate();

}
