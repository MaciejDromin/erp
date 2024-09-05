package com.soitio.finances.receipt.application.port;

import com.soitio.finances.receipt.domain.PurchaseItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseItemRepository extends JpaRepository<PurchaseItem, String> {
}
