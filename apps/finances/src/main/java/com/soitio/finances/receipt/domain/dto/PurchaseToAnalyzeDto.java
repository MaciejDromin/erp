package com.soitio.finances.receipt.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class PurchaseToAnalyzeDto {

    private String id;
    private BigDecimal amount;
    private String currency;
    private LocalDateTime date;
    private List<PurchaseItemToAnalyzeDto> items;

}
