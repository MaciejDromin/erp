package pl.mlisowski.finances.receipt.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mlisowski.finances.receipt.application.port.PurchaseRepository;
import pl.mlisowski.finances.receipt.domain.Purchase;
import pl.mlisowski.finances.receipt.domain.dto.ReceiptDto;

@Service
@RequiredArgsConstructor

public class ReceiptService {

    private final PurchaseFactory purchaseFactory;
    private final PurchaseRepository purchaseRepository;

    public void processReceipts(List<ReceiptDto> receipts) {
        List<Purchase> saved = purchaseRepository.saveAll(purchaseFactory.from(receipts));
        // convert to analytics object
        // publish rabbitMQ message for analytics
    }

}
