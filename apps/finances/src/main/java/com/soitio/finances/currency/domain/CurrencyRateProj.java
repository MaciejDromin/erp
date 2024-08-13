package com.soitio.finances.currency.domain;

import java.math.BigDecimal;

public interface CurrencyRateProj {

    String getUuid();

    String getCode();

    BigDecimal getRate();

}
