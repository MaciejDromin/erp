package com.soitio.widgets.finances.application;

import com.soitio.widgets.common.domain.data.Dataset;
import com.soitio.widgets.common.domain.data.Rgba;
import com.soitio.widgets.common.domain.data.WidgetData;
import com.soitio.widgets.finances.client.FinancesClient;
import com.soitio.widgets.finances.domain.MoneyOperationBalanceDto;
import com.soitio.widgets.finances.domain.MoneyOperationType;
import com.soitio.widgets.finances.domain.ObjectType;
import com.soitio.widgets.finances.domain.TotalObjectsValueDto;
import com.soitio.widgets.finances.inventory.client.InventoryClient;
import com.soitio.widgets.finances.inventory.domain.ObjectIdsDto;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

@Slf4j
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
        TotalObjectsValueDto totalValueItems = getTotalObjectValue(ObjectType.ITEM, inventoryClient::itemCount);
        TotalObjectsValueDto totalValueProperties = getTotalObjectValue(ObjectType.PROPERTY, inventoryClient::propertyCount);
//        TotalObjectsValueDto totalValueVehicles = getTotalObjectValue(ObjectType.VEHICLE, inventoryClient::objectCount);

        return WidgetData.builder()
                .labels(Set.of("Total NET Worth"))
                .datasets(Set.of(Dataset.builder()
                        .label("%s %s".formatted(Stream.of(
                                totalValueItems.getTotalAmount().getValue(),
                                totalValueProperties.getTotalAmount().getValue()
//                                totalValueVehicles.getTotalAmount().getValue()
                                ).reduce(BigDecimal.ZERO, BigDecimal::add),
                                totalValueItems.getTotalAmount().getCurrencyCode()))
                        .build()))
                .build();
    }

    private TotalObjectsValueDto getTotalObjectValue(ObjectType objectType,
                                                     Function<ObjectIdsDto, Map<String, Integer>> invFunc) {
        List<String> objectIds = financesClient.allObjectIds(objectType);
        Map<String, Integer> objectCountMap = invFunc.apply(ObjectIdsDto.of(objectIds));
        return financesClient.totalValue(objectCountMap, objectType);
    }

    public WidgetData calculateMonthlyBalance() {
        List<MoneyOperationBalanceDto> operations = financesClient.getOperationsForBalance(LocalDateTime.now().getYear());
        log.info("ML --- {}", operations);
        return WidgetData.builder()
                .labels(Set.of("M1", "M2", "M3"))
                .datasets(Set.of(Dataset.builder()
                        .data(List.of(5.2, 13.3, 6.0))
                        .label(MoneyOperationType.INCOME.name())
                        .backgroundColor(List.of(
                                new Rgba(0, 255, 0, 0.6)
                        ))
                        .borderColor(List.of(
                                new Rgba(0, 255, 0, 1)
                        ))
                        .borderWidth(2)
                        .build(), Dataset.builder()
                        .data(List.of(3.2, 23.3, 12.0))
                        .label(MoneyOperationType.EXPENSES.name())
                        .backgroundColor(List.of(
                                new Rgba(255, 0, 0, 0.6)
                        ))
                        .borderColor(List.of(
                                new Rgba(255, 0, 0, 1)
                        ))
                        .borderWidth(2)
                        .build()))
                .build();
    }
}
