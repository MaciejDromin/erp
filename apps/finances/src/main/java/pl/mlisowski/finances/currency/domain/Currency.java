package pl.mlisowski.finances.currency.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pl.mlisowski.finances.common.persistence.BaseEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Table(indexes = {
    @Index(name = "code_index", columnList = "code"),
    @Index(name = "date_code_index", columnList = "code, effectiveDate", unique = true),
    @Index(name = "date_index", columnList = "effectiveDate")
})
public class Currency extends BaseEntity {

    private String currencyName;

    @Column(length = 3)
    private String code;

    @Column(precision = 19, scale = 10)
    private BigDecimal rate;

    @Builder.Default
    private LocalDate effectiveDate = LocalDate.now();

}
