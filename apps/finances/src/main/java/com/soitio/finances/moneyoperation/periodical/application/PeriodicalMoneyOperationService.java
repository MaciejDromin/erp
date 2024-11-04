package com.soitio.finances.moneyoperation.periodical.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soitio.commons.dependency.DependencyCheckRequester;
import com.soitio.commons.dependency.DependencyCheckService;
import com.soitio.commons.dependency.model.DependencyCheckResult;
import com.soitio.commons.models.commons.MergePatch;
import com.soitio.commons.models.dto.finances.AmountDto;
import com.soitio.finances.common.AbstractDependencyCheckService;
import com.soitio.finances.moneyoperation.periodical.application.port.PeriodicalMoneyOperationRepository;
import com.soitio.finances.moneyoperation.periodical.domain.PeriodicalMoneyOperation;
import com.soitio.finances.moneyoperation.periodical.domain.QPeriodicalMoneyOperation;
import com.soitio.finances.moneyoperation.periodical.domain.dto.PeriodicalMoneyOperationCreationDto;
import com.soitio.finances.moneyoperation.periodical.domain.dto.PeriodicalMoneyOperationDto;
import com.soitio.finances.operationcategories.application.OperationCategoryService;
import com.soitio.finances.operationcategories.domain.OperationCategory;
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
        return null;
    }

    @Override
    protected PeriodicalMoneyOperation mapToEntity(MergePatch object) {
        return null;
    }

    @Override
    protected void updateEntity(PeriodicalMoneyOperation entity) {

    }
}
