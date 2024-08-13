package com.soitio.finances.receipt.application.port;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.soitio.finances.receipt.domain.Purchase;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, String> {
}
