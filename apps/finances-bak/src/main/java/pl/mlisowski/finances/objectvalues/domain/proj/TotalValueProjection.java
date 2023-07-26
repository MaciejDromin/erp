package pl.mlisowski.finances.objectvalues.domain.proj;

import java.math.BigDecimal;

public interface TotalValueProjection {

    BigDecimal getTotalAmount();
    String getCurrency();
    int getTotalCount();

}
