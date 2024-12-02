package com.soitio.finances.moneyoperation.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soitio.commons.dependency.DependencyCheckRequester;
import com.soitio.commons.dependency.DependencyCheckService;
import com.soitio.commons.dependency.model.DependencyCheckContext;
import com.soitio.commons.dependency.model.DependencyCheckDiff;
import com.soitio.commons.dependency.model.DependencyCheckResult;
import com.soitio.commons.models.commons.MergePatch;
import com.soitio.commons.models.dto.finances.AmountDto;
import com.soitio.commons.utils.DateUtils;
import com.soitio.finances.common.AbstractDependencyCheckService;
import com.soitio.finances.moneyoperation.application.port.MoneyOperationRepository;
import com.soitio.finances.moneyoperation.domain.MoneyOperation;
import com.soitio.finances.moneyoperation.domain.MoneyOperationType;
import com.soitio.finances.moneyoperation.domain.dto.MoneyOperationBalanceDto;
import com.soitio.finances.moneyoperation.domain.dto.MoneyOperationCreationDto;
import com.soitio.finances.moneyoperation.domain.dto.MoneyOperationDto;
import com.soitio.finances.operationcategories.application.OperationCategoryService;
import com.soitio.finances.operationcategories.domain.OperationCategory;
import com.soitio.finances.plannedexpenses.domain.PlannedExpenses;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MoneyOperationService extends AbstractDependencyCheckService<MoneyOperation> implements DependencyCheckService {

    private static final String SERVICE_NAME = "MoneyOperation";
    private final OperationCategoryService operationCategoryService;
    private final MoneyOperationRepository repository;

    public MoneyOperationService(ObjectMapper mapper,
                                 DependencyCheckRequester dependencyCheckRequester,
                                 OperationCategoryService operationCategoryService,
                                 MoneyOperationRepository repository) {
        super(mapper, dependencyCheckRequester);
        this.operationCategoryService = operationCategoryService;
        this.repository = repository;
    }

    public void create(MoneyOperationCreationDto creation) {
        repository.save(createObject(creation));
    }

    private MoneyOperation createObject(MoneyOperationCreationDto creation) {
        LocalDateTime effectiveDate = LocalDateTime.now(ZoneOffset.UTC);
        return MoneyOperation.builder()
                .amount(creation.getAmount().getValue())
                .effectiveDate(effectiveDate)
                .effectiveMonth(effectiveDate.getMonth())
                .effectiveYear(effectiveDate.getYear())
                .operationDescription(creation.getOperationDescription())
                .currency(creation.getAmount().getCurrencyCode())
                .operationType(creation.getOperationType())
                .operationCategory(operationCategoryService.getCategoryById(creation.getOperationCategoryId()))
                .build();
    }

    public Page<MoneyOperationDto> getPage(Pageable pageable) {
        return repository.findAll(pageable).map(this::from);
    }

    private MoneyOperationDto from(MoneyOperation moneyOperation) {
        var amount = moneyOperation.getAmount();
        return MoneyOperationDto.builder()
                .id(moneyOperation.getId())
                .amount(AmountDto.of(amount.getAmount(), amount.getCurrencyUnit().getCode()))
                .operationDescription(moneyOperation.getOperationDescription())
                .effectiveDate(moneyOperation.getEffectiveDate())
                .operationType(moneyOperation.getOperationType())
                .operationCategory(operationCategoryService.from(moneyOperation.getOperationCategory()))
                .build();
    }

    public void registerPlannedExpense(PlannedExpenses plannedExpenses) {
        repository.save(createFromPlannedExpense(plannedExpenses));
    }

    private MoneyOperation createFromPlannedExpense(PlannedExpenses plannedExpenses) {
        LocalDateTime effectiveDate = LocalDateTime.now(ZoneOffset.UTC);
        return MoneyOperation.builder()
                .amount(plannedExpenses.getActualAmount().getAmount())
                .effectiveDate(effectiveDate)
                .effectiveMonth(effectiveDate.getMonth())
                .effectiveYear(effectiveDate.getYear())
                .operationDescription(plannedExpenses.getOperationDescription())
                .currency(plannedExpenses.getActualAmount().getCurrencyUnit().getCode())
                .operationType(plannedExpenses.getOperationType())
                .operationCategory(plannedExpenses.getOperationCategory())
                .build();
    }

    public void saveAll(List<MoneyOperation> converted) {
        repository.saveAll(converted);
    }

    public List<MoneyOperationBalanceDto> getForBalance(int balanceYear, Month balanceMonth) {
        Supplier<List<MoneyOperation>> func;

        if (balanceMonth == null) func = () -> repository.findAllByEffectiveYear(balanceYear);
        else func = () -> repository.findAllByEffectiveYearAndEffectiveMonth(balanceYear, balanceMonth);

        return func.get().stream()
                .map(this::toBalance)
                .toList();
    }

    private MoneyOperationBalanceDto toBalance(MoneyOperation moneyOperation) {
        return MoneyOperationBalanceDto.builder()
                .id(moneyOperation.getId())
                .amount(AmountDto.of(moneyOperation.getAmount().getAmount(),
                        moneyOperation.getAmount().getCurrencyUnit().getCode()))
                .effectiveYear(moneyOperation.getEffectiveYear())
                .effectiveMonth(moneyOperation.getEffectiveMonth())
                .operationType(moneyOperation.getOperationType())
                .build();
    }

    public List<Integer> findDistinctEffectiveYears() {
        return repository.findDistinctEffectiveYearBy();
    }

    public List<Month> findDistinctEffectiveMonths(int year) {
        return repository.findDistinctEffectiveMonthByEffectiveYear(year);
    }

    @Override
    public String getServiceName() {
        return SERVICE_NAME;
    }

    @Override
    public Set<DependencyCheckResult> checkForEdit(Set<DependencyCheckContext> set) {
        Optional<DependencyCheckDiff> optDiff = set.stream()
                .map(DependencyCheckContext::diff)
                .flatMap(Collection::stream)
                .filter(diff -> diff.field().equals("operationType"))
                .findAny();
        List<MoneyOperation> ops = repository.findAllByOperationCategoryIdIn(set.stream()
                .map(DependencyCheckContext::id)
                .collect(Collectors.toSet()));
        if (optDiff.isPresent() && !ops.isEmpty()) {
            return ops.stream()
                    .map(MoneyOperation::getOperationCategory)
                    .map(OperationCategory::getId)
                    .map(id -> new DependencyCheckResult(id, true, "Operation category is in use"))
                    .collect(Collectors.toSet());
        }
        return Set.of();
    }

    @Override
    public Set<DependencyCheckResult> checkForDelete(Set<DependencyCheckContext> set) {
        return repository.findAllByOperationCategoryIdIn(set.stream()
                        .map(DependencyCheckContext::id)
                        .collect(Collectors.toSet()))
                .stream()
                .map(MoneyOperation::getOperationCategory)
                .map(OperationCategory::getId)
                .map(id -> new DependencyCheckResult(id, true, "Operation category with id '%s' is in use!".formatted(id)))
                .collect(Collectors.toSet());
    }

    @Override
    public void deleteByIds(Set<String> collect) {
        repository.deleteAllById(collect);
    }

    @Override
    protected MoneyOperation findById(String id) {
        return repository.getReferenceById(id);
    }

    @Override
    protected MoneyOperation mapToEntity(MergePatch object) {
        var fields = object.getObjectValue();
        var opCat = fields.get("operationCategory").getObjectValue();
        var amount = fields.get("amount").getObjectValue();
        BigDecimal value;
        try {
            value = new BigDecimal(amount.get("value").getStrValue());
        } catch (Exception e) {
            throw new IllegalStateException("Incorrect value " + amount.get("value").getStrValue());
        }
        LocalDateTime effectiveDate = DateUtils.localDateTimeFromString(fields.get("effectiveDate").getStrValue());
        return MoneyOperation.builder()
                .id(fields.get("id").getStrValue())
                .amount(value)
                .currency(amount.get("currencyCode").getStrValue())
                .operationDescription(fields.get("operationDescription").getStrValue())
                .effectiveDate(effectiveDate)
                .effectiveMonth(effectiveDate.getMonth())
                .effectiveYear(effectiveDate.getYear())
                .operationType(MoneyOperationType.valueOf(fields.get("operationType").getStrValue()))
                .operationCategory(OperationCategory.builder()
                        .id(opCat.get("id").getStrValue())
                        .build())
                .build();
    }

    @Override
    protected void updateEntity(MoneyOperation entity) {
        repository.save(entity);
    }

    @Override
    protected Object mapToDto(MoneyOperation entity) {
        return from(entity);
    }

    public MoneyOperationDto getMoneyOperation(String id) {
        return from(findById(id));
    }
}
