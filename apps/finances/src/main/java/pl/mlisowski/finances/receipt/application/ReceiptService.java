package pl.mlisowski.finances.receipt.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mlisowski.finances.common.messaging.RabbitPublisher;
import pl.mlisowski.finances.receipt.application.port.PurchaseRepository;
import pl.mlisowski.finances.receipt.domain.Purchase;
import pl.mlisowski.finances.receipt.domain.PurchaseItem;
import pl.mlisowski.finances.receipt.domain.dto.PurchaseItemToAnalyzeDto;
import pl.mlisowski.finances.receipt.domain.dto.PurchaseToAnalyzeDto;
import pl.mlisowski.finances.receipt.domain.dto.ReceiptDto;

@Service
@RequiredArgsConstructor

public class ReceiptService {

    private final PurchaseFactory purchaseFactory;
    private final PurchaseRepository purchaseRepository;
    private final RabbitPublisher rabbitPublisher;

    public void processReceipts(List<ReceiptDto> receipts) {
        List<Purchase> saved = purchaseRepository.saveAll(purchaseFactory.from(receipts));
        // convert to analytics object
        List<PurchaseToAnalyzeDto> converted = convertPurchasesToAnalyze(saved);
        // publish rabbitMQ message for analytics
        rabbitPublisher.publish("purchase_analytics_queue", converted);
    }

    private List<PurchaseToAnalyzeDto> convertPurchasesToAnalyze(List<Purchase> saved) {
        return saved.stream()
                .map(p -> PurchaseToAnalyzeDto.builder()
                        .id(p.getUuid())
                        .amount(p.getRawAmount())
                        .currency(p.getCurrency())
                        .date(p.getDate())
                        .items(convertItemsToAnalyze(p.getPurchaseItems()))
                        .build())
                .toList();
    }

    private List<PurchaseItemToAnalyzeDto> convertItemsToAnalyze(List<PurchaseItem> purchaseItems) {
        return purchaseItems.stream()
                .map(i -> PurchaseItemToAnalyzeDto.builder()
                        .id(i.getUuid())
                        .price(i.getPrice())
                        .unitPrice(i.getUnitPrice())
                        .quantity(i.getQuantity())
                        .currency(i.getCurrency())
                        .build())
                .toList();
    }

}
