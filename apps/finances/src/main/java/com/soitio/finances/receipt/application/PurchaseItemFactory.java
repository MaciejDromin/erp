package com.soitio.finances.receipt.application;

import com.soitio.finances.receipt.domain.PurchaseItem;
import com.soitio.finances.receipt.domain.dto.ReceiptItemDto;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class PurchaseItemFactory {

    public List<PurchaseItem> from(List<ReceiptItemDto> items) {
        return items.parallelStream()
                .map(this::buildPurchaseItem)
                .toList();
    }

    private PurchaseItem buildPurchaseItem(ReceiptItemDto item) {
        return PurchaseItem.builder()
                .name(item.getName())
                .unit(item.getUnit())
                .unitPrice(BigDecimal.valueOf(item.getUnitPrice()))
                .price(BigDecimal.valueOf(item.getPrice()))
                .quantity(item.getQuantity())
                .currency("PLN") // TODO: Make this dynamic
                .build();
    }

}
