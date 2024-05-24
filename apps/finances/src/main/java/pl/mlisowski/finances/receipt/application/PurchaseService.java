package pl.mlisowski.finances.receipt.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mlisowski.finances.receipt.application.port.PurchaseRepository;
import pl.mlisowski.finances.receipt.domain.Purchase;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;

    public Purchase getById(String id) {
        return purchaseRepository.getReferenceById(id);
    }

    public List<Purchase> saveAll(List<Purchase> purchases) {
        return purchaseRepository.saveAll(purchases);
    }

}
