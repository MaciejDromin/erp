package com.soitio.finances.receipt.application.port;

import com.soitio.finances.common.persistence.OrgRepository;
import com.soitio.finances.receipt.domain.Purchase;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends OrgRepository<Purchase, String> {
}
