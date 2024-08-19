package com.soitio.widgets.finances.application;

import com.soitio.widgets.common.domain.data.Dataset;
import com.soitio.widgets.common.domain.data.Rgba;
import com.soitio.widgets.common.domain.data.WidgetData;
import com.soitio.widgets.finances.client.FinancesClient;
import com.soitio.widgets.finances.common.Pair;
import com.soitio.widgets.finances.domain.MoneyOperationBalanceDto;
import com.soitio.widgets.finances.domain.MoneyOperationType;
import com.soitio.widgets.finances.domain.ObjectType;
import com.soitio.widgets.finances.domain.TotalObjectsValueDto;
import com.soitio.widgets.finances.inventory.client.InventoryClient;
import com.soitio.widgets.finances.inventory.domain.ObjectIdsDto;
import jakarta.inject.Singleton;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        TotalObjectsValueDto totalValueVehicles = getTotalObjectValue(ObjectType.VEHICLE, inventoryClient::vehicleCount);

        return WidgetData.builder()
                .labels(List.of("Total NET Worth"))
                .datasets(List.of(Dataset.builder()
                        .label("%s %s".formatted(Stream.of(
                                totalValueItems.getTotalAmount().getValue(),
                                totalValueProperties.getTotalAmount().getValue(),
                                totalValueVehicles.getTotalAmount().getValue()
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
        List<MoneyOperationBalanceDto> operations = financesClient.getOperationsForBalance(LocalDateTime.now().getYear(), null);
        return WidgetData.builder()
                .labels(Arrays.stream(Month.values())
                        .map(Enum::name)
                        .toList())
                .datasets(createBalanceDatasets(operations))
                .build();
    }

    private List<Dataset> createBalanceDatasets(List<MoneyOperationBalanceDto> operations) {
        Map<Pair<MoneyOperationType, Month>, BigDecimal> initMap = new HashMap<>();
        initializeMapForBalance(initMap);
        Map<Pair<MoneyOperationType, Month>, BigDecimal> totalPerMonth = operations.stream()
                .collect(Collectors.toMap(
                        op -> new Pair<>(op.getOperationType(), op.getEffectiveMonth()),
                        op -> op.getAmount().getValue(),
                        BigDecimal::add,
                        () -> initMap));

        Dataset incomeDataset = buildBalanceDataset(totalPerMonth, MoneyOperationType.INCOME);
        Dataset expenseDataset = buildBalanceDataset(totalPerMonth, MoneyOperationType.EXPENSES);

        return List.of(incomeDataset, expenseDataset);
    }

    private void initializeMapForBalance(Map<Pair<MoneyOperationType, Month>, BigDecimal> mapToInit) {
        Arrays.stream(Month.values())
                .forEach(m -> {
                    mapToInit.put(new Pair<>(MoneyOperationType.INCOME, m), BigDecimal.ZERO);
                    mapToInit.put(new Pair<>(MoneyOperationType.EXPENSES, m), BigDecimal.ZERO);
                });
    }

    private Dataset buildBalanceDataset(Map<Pair<MoneyOperationType, Month>, BigDecimal> totalPerMonth,
                                        MoneyOperationType operationType) {
        int red = operationType == MoneyOperationType.EXPENSES ? 255 : 0;
        int green = 255 - red;
        return Dataset.builder()
                .data(totalPerMonth.entrySet().stream()
                        .filter(e -> e.getKey().left() == operationType)
                        .sorted(Comparator.comparingInt(e -> e.getKey().right().ordinal()))
                        .map(Map.Entry::getValue)
                        .map(BigDecimal::doubleValue)
                        .toList())
                .label(operationType.name())
                .backgroundColor(List.of(
                        new Rgba(red, green, 0, 0.6)
                ))
                .borderColor(List.of(
                        new Rgba(red, green, 0, 1)
                ))
                .borderWidth(2)
                .build();
    }

    public WidgetData calculateMonthlyBalanceDiff(int year, Month month) {
        List<MoneyOperationBalanceDto> operations = financesClient.getOperationsForBalance(year, month);
        return WidgetData.builder().build();
    }
}
