package com.soitio.finances.moneyoperation.periodical.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soitio.commons.dependency.DependencyCheckRequester;
import com.soitio.commons.dependency.DependencyCheckService;
import com.soitio.commons.dependency.model.DependencyCheckContext;
import com.soitio.commons.dependency.model.DependencyCheckDiff;
import com.soitio.commons.dependency.model.DependencyCheckResult;
import com.soitio.commons.models.commons.MergePatch;
import com.soitio.commons.models.dto.finances.AmountDto;
import com.soitio.commons.models.dto.finances.MoneyOperationType;
import com.soitio.finances.common.AbstractDependencyCheckService;
import com.soitio.finances.moneyoperation.periodical.application.port.PeriodicalMoneyOperationRepository;
import com.soitio.finances.moneyoperation.periodical.domain.PeriodicalMoneyOperation;
import com.soitio.finances.moneyoperation.periodical.domain.QPeriodicalMoneyOperation;
import com.soitio.finances.moneyoperation.periodical.domain.dto.PeriodicalMoneyOperationCreationDto;
import com.soitio.finances.moneyoperation.periodical.domain.dto.PeriodicalMoneyOperationDto;
import com.soitio.finances.operationcategories.application.OperationCategoryService;
import com.soitio.finances.operationcategories.domain.OperationCategory;
import java.math.BigDecimal;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PeriodicalMoneyOperationService extends AbstractDependencyCheckService<PeriodicalMoneyOperation> implements DependencyCheckService {

    private static final String SERVICE_NAME = "PeriodicalMoneyOperation";
    private final OperationCategoryService operationCategoryService;
    private final PeriodicalMoneyOperationRepository repository;

    public PeriodicalMoneyOperationService(ObjectMapper mapper,
                                           DependencyCheckRequester dependencyCheckRequester,
                                           OperationCategoryService operationCategoryService,
                                           PeriodicalMoneyOperationRepository repository) {
        super(mapper, dependencyCheckRequester);
        this.operationCategoryService = operationCategoryService;
        this.repository = repository;
    }

    public Page<PeriodicalMoneyOperationDto> getPage(Pageable pageable) {
        return repository.findAll(pageable).map(this::from);
    }

    public void create(PeriodicalMoneyOperationCreationDto creation) {
        repository.save(createObject(creation));
    }

    private PeriodicalMoneyOperation createObject(PeriodicalMoneyOperationCreationDto creation) {
        return PeriodicalMoneyOperation.builder()
                .amount(creation.getAmount().getValue())
                .operationDescription(creation.getOperationDescription())
                .currency(creation.getAmount().getCurrencyCode())
                .repetitionPeriod(creation.getRepetitionPeriod())
                .operationType(creation.getOperationType())
                .nextApplicableMonth(creation.getNextApplicableMonth())
                .operationCategory(operationCategoryService.getCategoryById(creation.getOperationCategoryId()))
                .build();
    }

    private PeriodicalMoneyOperationDto from(PeriodicalMoneyOperation periodicalMoneyOperation) {
        var amount = periodicalMoneyOperation.getAmount();
        return PeriodicalMoneyOperationDto.builder()
                .id(periodicalMoneyOperation.getId())
                .amount(AmountDto.of(amount.getAmount(), amount.getCurrencyUnit().getCode()))
                .operationDescription(periodicalMoneyOperation.getOperationDescription())
                .repetitionPeriod(periodicalMoneyOperation.getRepetitionPeriod())
                .operationType(periodicalMoneyOperation.getOperationType())
                .nextApplicableMonth(periodicalMoneyOperation.getNextApplicableMonth())
                .operationCategory(operationCategoryService.from(periodicalMoneyOperation.getOperationCategory()))
                .build();
    }

    public List<PeriodicalMoneyOperation> getOperationsForMonth(Month accountingMonth) {
        QPeriodicalMoneyOperation operation = QPeriodicalMoneyOperation.periodicalMoneyOperation;
        List<PeriodicalMoneyOperation> ret = new ArrayList<>();
        var operations = operation.nextApplicableMonth.eq(accountingMonth);
        repository.findAll(operations).forEach(ret::add);
        return ret;
    }

    public void saveAll(List<PeriodicalMoneyOperation> operationList) {
        repository.saveAll(operationList);
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
        List<PeriodicalMoneyOperation> ops = repository.findAllByOperationCategoryIdIn(set.stream()
                .map(DependencyCheckContext::id)
                .collect(Collectors.toSet()));

        if (optDiff.isPresent() && !ops.isEmpty()) {
            return ops.stream()
                    .map(PeriodicalMoneyOperation::getOperationCategory)
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
                .map(PeriodicalMoneyOperation::getOperationCategory)
                .map(OperationCategory::getId)
                .map(id -> new DependencyCheckResult(id, true, "Operation category with id '%s' is in use!".formatted(id)))
                .collect(Collectors.toSet());
    }

    @Override
    public void deleteByIdsAndOrgId(Collection<String> collect, String orgId) {
        repository.deleteAllById(collect);
    }

    @Override
    protected PeriodicalMoneyOperation findByIdAndOrgId(String id, String orgId) {
        return repository.getReferenceById(id);
    }

    @Override
    protected PeriodicalMoneyOperation mapToEntity(MergePatch object) {
        var fields = object.getObjectValue();
        var opCat = fields.get("operationCategory").getObjectValue();
        var amount = fields.get("amount").getObjectValue();
        BigDecimal value;
        try {
            value = new BigDecimal(amount.get("value").getStrValue());
        } catch (Exception e) {
            throw new IllegalStateException("Incorrect value " + amount.get("value").getStrValue());
        }
        return PeriodicalMoneyOperation.builder()
                .id(fields.get("id").getStrValue())
                .amount(value)
                .currency(amount.get("currencyCode").getStrValue())
                .operationDescription(fields.get("operationDescription").getStrValue())
                .repetitionPeriod(fields.get("repetitionPeriod").getIntValue())
                .operationType(MoneyOperationType.valueOf(fields.get("operationType").getStrValue()))
                .nextApplicableMonth(Month.valueOf(fields.get("nextApplicableMonth").getStrValue()))
                .operationCategory(OperationCategory.builder()
                        .id(opCat.get("id").getStrValue())
                        .build())
                .build();
    }

    @Override
    protected void updateEntity(PeriodicalMoneyOperation entity) {
        repository.save(entity);
    }

    @Override
    protected Object mapToDto(PeriodicalMoneyOperation entity) {
        return PeriodicalMoneyOperationDto.builder()
                .id(entity.getId())
                .amount(AmountDto.of(entity.getAmount().getAmount(), entity.getCurrency()))
                .operationDescription(entity.getOperationDescription())
                .repetitionPeriod(entity.getRepetitionPeriod())
                .operationType(entity.getOperationType())
                .nextApplicableMonth(entity.getNextApplicableMonth())
                .operationCategory(operationCategoryService.from(entity.getOperationCategory()))
                .build();
    }

    public PeriodicalMoneyOperationDto getPeriodicalOperation(String id) {
        return from(findByIdAndOrgId(id, ));
    }
}
