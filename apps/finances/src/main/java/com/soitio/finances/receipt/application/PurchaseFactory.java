package com.soitio.finances.receipt.application;

import com.soitio.finances.receipt.domain.Purchase;
import com.soitio.finances.receipt.domain.PurchaseItem;
import com.soitio.finances.receipt.domain.dto.OrgWrapper;
import com.soitio.finances.receipt.domain.dto.ReceiptDto;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PurchaseFactory {

    private final PurchaseItemFactory purchaseItemFactory;

    public List<Purchase> from(OrgWrapper<List<ReceiptDto>> receipts) {
        return receipts.data().parallelStream()
                .map(r -> buildPurchase(r, receipts.orgId()))
                .toList();
    }

    private Purchase buildPurchase(ReceiptDto receipt, String orgId) {
        var items = purchaseItemFactory.from(receipt.getItems(), orgId);
        var purchase = Purchase.builder()
                .address(receipt.getAddress())
                .amount(items.stream()
                        .map(PurchaseItem::getPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .currency("PLN") // TODO: Make this dynamic
                .date(receipt.getDate().atStartOfDay())
                .source(receipt.getSource())
                .orgId(orgId)
                .build();
        purchase.setPurchaseItems(items);
        return purchase;
    }

}
