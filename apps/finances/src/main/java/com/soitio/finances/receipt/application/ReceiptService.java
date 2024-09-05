package com.soitio.finances.receipt.application;

import com.soitio.finances.common.messaging.RabbitPublisher;
import com.soitio.finances.moneyoperation.application.MoneyOperationService;
import com.soitio.finances.moneyoperation.domain.MoneyOperation;
import com.soitio.finances.moneyoperation.domain.MoneyOperationType;
import com.soitio.finances.receipt.domain.Purchase;
import com.soitio.finances.receipt.domain.PurchaseItem;
import com.soitio.finances.receipt.domain.dto.PurchaseItemToAnalyzeDto;
import com.soitio.finances.receipt.domain.dto.PurchaseToAnalyzeDto;
import com.soitio.finances.receipt.domain.dto.ReceiptDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class ReceiptService {

    private final PurchaseFactory purchaseFactory;
    private final PurchaseService purchaseService;
    private final RabbitPublisher rabbitPublisher;
    private final MoneyOperationService moneyOperationService;

    public void processReceipts(List<ReceiptDto> receipts) {
        List<Purchase> saved = purchaseService.saveAll(purchaseFactory.from(receipts));
        convertToMoneyOperations(saved);
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

    private void convertToMoneyOperations(List<Purchase> purchases) {
        moneyOperationService.saveAll(purchases.stream()
                .map(this::convertToMoneyOperation)
                .toList());
    }

    // Operation for now, should be manually set by user
    private MoneyOperation convertToMoneyOperation(Purchase purchase) {
        return MoneyOperation.builder()
                .amount(purchase.getAmount().getAmount())
                .effectiveDate(purchase.getDate())
                .effectiveMonth(purchase.getDate().getMonth())
                .effectiveYear(purchase.getDate().getYear())
                .operationDescription(purchase.getSource())
                .currency(purchase.getCurrency())
                .operationType(MoneyOperationType.EXPENSES)
                .build();
    }

}
