package com.soitio.finances.receipt.application.port;

import com.soitio.finances.common.persistence.OrgRepository;
import com.soitio.finances.receipt.domain.PurchaseItem;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseItemRepository extends OrgRepository<PurchaseItem, String> {
}
