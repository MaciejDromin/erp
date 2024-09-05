package com.soitio.finances.receipt.application;

import com.soitio.finances.receipt.application.port.PurchaseRepository;
import com.soitio.finances.receipt.domain.Purchase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
