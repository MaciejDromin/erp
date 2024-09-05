package com.soitio.finances.receipt.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.soitio.finances.receipt.domain.ItemUnit;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class ReceiptItemDto {

    private String name;
    private ItemUnit unit;

    @JsonProperty("unit_price")
    private double unitPrice;
    private double price;
    private double quantity;

}
