package com.soitio.finances.moneyoperation.periodical.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soitio.commons.dependency.DependencyCheckRequester;
import com.soitio.commons.dependency.DependencyCheckService;
import com.soitio.commons.dependency.model.DependencyCheckResult;
import com.soitio.commons.models.commons.MergePatch;
import com.soitio.commons.models.dto.finances.AmountDto;
import com.soitio.finances.common.AbstractDependencyCheckService;
import com.soitio.finances.moneyoperation.domain.MoneyOperationType;
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
import java.util.List;
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
                .uuid(periodicalMoneyOperation.getUuid())
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
    public Set<DependencyCheckResult> checkForEdit(Set<String> set) {
        return Set.of();
    }

    @Override
    public Set<DependencyCheckResult> checkForDelete(Set<String> set) {
        return repository.findAllByOperationCategoryUuidIn(set)
                .stream()
                .map(PeriodicalMoneyOperation::getOperationCategory)
                .map(OperationCategory::getUuid)
                .map(id -> new DependencyCheckResult(id, true, "Operation category with id '%s' is in use!".formatted(id)))
                .collect(Collectors.toSet());
    }

    @Override
    public void deleteByIds(Set<String> collect) {
        repository.deleteAllById(collect);
    }

    @Override
    protected PeriodicalMoneyOperation findById(String id) {
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
                .uuid(fields.get("uuid").getStrValue())
                .amount(value)
                .currency(amount.get("currencyCode").getStrValue())
                .operationDescription(fields.get("operationDescription").getStrValue())
                .repetitionPeriod(fields.get("repetitionPeriod").getIntValue())
                .operationType(MoneyOperationType.valueOf(fields.get("operationType").getStrValue()))
                .nextApplicableMonth(Month.valueOf(fields.get("nextApplicableMonth").getStrValue()))
                .operationCategory(OperationCategory.builder()
                        .uuid(opCat.get("uuid").getStrValue())
                        .operationType(MoneyOperationType.valueOf(opCat.get("operationType").getStrValue()))
                        .operationName(opCat.get("operationName").getStrValue())
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
                .uuid(entity.getUuid())
                .amount(AmountDto.of(entity.getAmount().getAmount(), entity.getCurrency()))
                .operationDescription(entity.getOperationDescription())
                .repetitionPeriod(entity.getRepetitionPeriod())
                .operationType(entity.getOperationType())
                .nextApplicableMonth(entity.getNextApplicableMonth())
                .operationCategory(operationCategoryService.from(entity.getOperationCategory()))
                .build();
    }

    public PeriodicalMoneyOperationDto getPeriodicalOperation(String id) {
        return from(findById(id));
    }
}
