package com.soitio.widgets.finances.application;

import com.soitio.commons.models.dto.PageDto;
import com.soitio.commons.models.dto.finances.AmountDto;
import com.soitio.commons.models.dto.finances.ObjectValueDto;
import com.soitio.commons.models.dto.finances.PlannedExpensesDto;
import com.soitio.commons.models.dto.finances.TopItemByCategoryDto;
import com.soitio.commons.models.dto.inventory.category.CategoryDto;
import com.soitio.commons.models.dto.inventory.item.InventoryItemDto;
import com.soitio.commons.utils.PageableDataFetcher;
import com.soitio.widgets.common.domain.data.Dataset;
import com.soitio.widgets.common.domain.data.Rgba;
import com.soitio.widgets.common.domain.data.WidgetData;
import com.soitio.widgets.finances.client.FinancesClient;
import com.soitio.widgets.finances.common.Pair;
import com.soitio.widgets.finances.domain.MoneyOperationBalanceDto;
import com.soitio.widgets.finances.domain.MoneyOperationType;
import com.soitio.widgets.finances.domain.ObjectType;
import com.soitio.widgets.finances.domain.CategoryWrapper;
import com.soitio.widgets.finances.domain.TotalObjectsValueDto;
import com.soitio.widgets.finances.inventory.client.InventoryClient;
import com.soitio.widgets.finances.inventory.domain.ObjectIdsDto;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
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
        BigDecimal income = sumAmountsByType(operations, MoneyOperationType.INCOME);
        BigDecimal expenses = sumAmountsByType(operations, MoneyOperationType.EXPENSES);
        return WidgetData.builder()
                .labels(List.of("Monthly Balance Diff"))
                .datasets(List.of(Dataset.builder()
                        .label("%s".formatted(income.subtract(expenses)))
                        .build()))
                .build();
    }

    private BigDecimal sumAmountsByType(List<MoneyOperationBalanceDto> moneyOperations, MoneyOperationType type) {
        return moneyOperations.stream()
                .filter(m -> m.getOperationType() == type)
                .map(MoneyOperationBalanceDto::getAmount)
                .map(AmountDto::getValue).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public WidgetData getMostExpensiveItemPerCat() {
        // Get all items
        List<InventoryItemDto> items = PageableDataFetcher.fetchDataWithNoParams(inventoryClient::getAllItems);
        // grep by categories
        Map<CategoryDto, Set<String>> itemsGrouped = groupItems(items);

        // parallelize by category to fetch single highest value item
        List<CategoryWrapper<TopItemByCategoryDto>> wrapped = getByCategoryWrapped(itemsGrouped,
                financesClient::findTopByObjectIdsIn);
        // create chart data

        List<String> labels = new ArrayList<>();
        List<Double> data = new ArrayList<>();

        for (CategoryWrapper<TopItemByCategoryDto> wrapper : wrapped) {
            labels.add(wrapper.getCategory().getName());
            data.add(wrapper.getWrapped().getAmount().getValue().doubleValue());
        }

        return WidgetData.builder()
                .labels(labels)
                .datasets(List.of(Dataset.builder()
                        .data(data)
                        .build()))
                .build();
    }

    public WidgetData getValuePerCategory() {
        // Get all items
        List<InventoryItemDto> items = PageableDataFetcher.fetchDataWithNoParams(inventoryClient::getAllItems);
        // grep by categories
        Map<CategoryDto, Set<String>> itemsGrouped = groupItems(items);

        // parallelize by category to fetch object value by item ids

        List<CategoryWrapper<PageDto<ObjectValueDto>>> wrapped = getByCategoryWrapped(itemsGrouped,
                s -> financesClient.getObjectValues(s.size(), ObjectType.ITEM, s));

        // create chart data

        List<String> labels = new ArrayList<>();
        List<Double> data = new ArrayList<>();

        for (CategoryWrapper<PageDto<ObjectValueDto>> wrapper : wrapped) {
            labels.add(wrapper.getCategory().getName());
            data.add(wrapper.getWrapped().getContent().stream()
                    .map(ObjectValueDto::getAmount)
                    .map(AmountDto::getValue)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .doubleValue());
        }

        return WidgetData.builder()
                .labels(labels)
                .datasets(List.of(Dataset.builder()
                        .data(data)
                        .build()))
                .build();
    }

    private <T> List<CategoryWrapper<T>> getByCategoryWrapped(Map<CategoryDto, Set<String>> itemsGrouped,
                                                              Function<Set<String>, T> fetchFunction) {
        return itemsGrouped.entrySet().parallelStream()
                .map(e -> CategoryWrapper.of(e.getKey(), fetchFunction.apply(e.getValue())))
                .toList();
    }

    private static Map<CategoryDto, Set<String>> groupItems(List<InventoryItemDto> items) {
        Map<CategoryDto, Set<String>> itemsGrouped = new HashMap<>();
        for (InventoryItemDto item : items) {
            for (CategoryDto category : item.getCategories()) {
                if (itemsGrouped.containsKey(category)) {
                    itemsGrouped.get(category).add(item.getId());
                    continue;
                }
                Set<String> tmp = new HashSet<>();
                tmp.add(item.getId());
                itemsGrouped.put(category, tmp);
            }
        }
        return itemsGrouped;
    }

    public WidgetData getRemainingPlannedExpenses() {
        LocalDate today = LocalDate.now();
        Map<String, String> params = Map.of("year", String.valueOf(today.getYear()),
                "month", today.getMonth().toString());
        List<PlannedExpensesDto> data = PageableDataFetcher.fetchData(financesClient::getPlannedExpenses, params);
        return WidgetData.builder()
                .labels(List.of("Remaining Planned Expenses"))
                .datasets(List.of(Dataset.builder()
                        .label("%s".formatted(data.stream()
                                .filter(Predicate.not(PlannedExpensesDto::isFinalized))
                                .map(PlannedExpensesDto::getPlannedAmount)
                                .map(AmountDto::getValue)
                                .reduce(BigDecimal.ZERO, BigDecimal::add)))
                        .build()))
                .build();
    }
}
