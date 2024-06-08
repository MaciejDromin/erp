package pl.mlisowski.finances.receipt.application;

import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.mlisowski.finances.receipt.domain.Purchase;
import pl.mlisowski.finances.receipt.domain.PurchaseItem;
import pl.mlisowski.finances.receipt.domain.dto.ReceiptDto;

@Component
@RequiredArgsConstructor
public class PurchaseFactory {

    private final PurchaseItemFactory purchaseItemFactory;

    public List<Purchase> from(List<ReceiptDto> receipts) {
        return receipts.parallelStream()
                .map(this::buildPurchase)
                .toList();
    }

    private Purchase buildPurchase(ReceiptDto receipt) {
        var items = purchaseItemFactory.from(receipt.getItems());
        var purchase = Purchase.builder()
                .address(receipt.getAddress())
                .amount(items.stream()
                        .map(PurchaseItem::getPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .currency("PLN") // TODO: Make this dynamic
                .date(receipt.getDate())
                .source(receipt.getSource())
                .build();
        purchase.setPurchaseItems(items);
        return purchase;
    }

}
