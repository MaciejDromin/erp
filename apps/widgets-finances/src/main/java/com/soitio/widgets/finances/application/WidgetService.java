package com.soitio.widgets.finances.application;

import com.soitio.widgets.common.domain.data.Dataset;
import com.soitio.widgets.common.domain.data.WidgetData;
import com.soitio.widgets.finances.client.FinancesClient;
import com.soitio.widgets.finances.domain.TotalObjectsValueDto;
import com.soitio.widgets.finances.inventory.client.InventoryClient;
import com.soitio.widgets.finances.inventory.domain.ObjectIdsDto;
import jakarta.inject.Singleton;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Singleton
public class WidgetService {

    private final FinancesClient financesClient;
    private final InventoryClient inventoryClient;

    public WidgetService(@RestClient FinancesClient financesClient,
                         @RestClient InventoryClient inventoryClient) {
        this.financesClient = financesClient;
        this.inventoryClient = inventoryClient;
    }

    public WidgetData calculateTotalNetWorth() {
        List<String> objectIds = financesClient.allObjectIds();
        Map<String, Integer> objectCountMap = inventoryClient.objectCount(ObjectIdsDto.of(objectIds));
        TotalObjectsValueDto totalValue = financesClient.totalValue(objectCountMap);
        return WidgetData.builder()
                .labels(Set.of("Total NET Worth"))
                .datasets(Set.of(Dataset.builder()
                        .label("%s %s".formatted(totalValue.getTotalAmount().getValue(),
                                totalValue.getTotalAmount().getCurrencyCode()))
                        .build()))
                .build();
    }

}
