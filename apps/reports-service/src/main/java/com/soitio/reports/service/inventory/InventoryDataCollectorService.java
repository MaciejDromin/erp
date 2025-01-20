package com.soitio.reports.service.inventory;

import com.soitio.commons.utils.PageableDataFetcher;
import com.soitio.reports.service.inventory.client.InventoryClient;
import com.soitio.reports.service.inventory.domain.InventoryItemDto;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class InventoryDataCollectorService {

    private final InventoryClient inventoryClient;

    public InventoryDataCollectorService(@RestClient InventoryClient inventoryClient) {
        this.inventoryClient = inventoryClient;
    }

    public List<InventoryItemDto> getAllItems(String orgId) {
        return PageableDataFetcher.fetchData(p -> inventoryClient.getAllItems(orgId, p), Map.of());
    }
}
