package com.soitio.finances.moneyoperation.periodical.application;

import com.soitio.commons.models.dto.finances.AmountDto;
import com.soitio.finances.moneyoperation.periodical.application.port.PeriodicalMoneyOperationRepository;
import com.soitio.finances.moneyoperation.periodical.domain.PeriodicalMoneyOperaion;
import com.soitio.finances.moneyoperation.periodical.domain.QPeriodicalMoneyOperaion;
import com.soitio.finances.moneyoperation.periodical.domain.dto.PeriodicalMoneyOperationCreationDto;
import com.soitio.finances.moneyoperation.periodical.domain.dto.PeriodicalMoneyOperationDto;
import com.soitio.finances.operationcategories.application.OperationCategoryService;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PeriodicalMoneyOperationService {

    private final OperationCategoryService operationCategoryService;
    private final PeriodicalMoneyOperationRepository repository;

    public Page<PeriodicalMoneyOperationDto> getPage(Pageable pageable) {
        return repository.findAll(pageable).map(this::from);
    }

    public void create(PeriodicalMoneyOperationCreationDto creation) {
        repository.save(createObject(creation));
    }

    private PeriodicalMoneyOperaion createObject(PeriodicalMoneyOperationCreationDto creation) {
        return PeriodicalMoneyOperaion.builder()
                .amount(creation.getAmount().getValue())
                .operationDescription(creation.getOperationDescription())
                .currency(creation.getAmount().getCurrencyCode())
                .repetitionPeriod(creation.getRepetitionPeriod())
                .operationType(creation.getOperationType())
                .nextApplicableMonth(creation.getNextApplicableMonth())
                .operationCategory(operationCategoryService.getCategoryById(creation.getOperationCategoryId()))
                .build();
    }

    private PeriodicalMoneyOperationDto from(PeriodicalMoneyOperaion periodicalMoneyOperaion) {
        var amount = periodicalMoneyOperaion.getAmount();
        return PeriodicalMoneyOperationDto.builder()
                .uuid(periodicalMoneyOperaion.getUuid())
                .amount(AmountDto.of(amount.getAmount(), amount.getCurrencyUnit().getCode()))
                .operationDescription(periodicalMoneyOperaion.getOperationDescription())
                .repetitionPeriod(periodicalMoneyOperaion.getRepetitionPeriod())
                .operationType(periodicalMoneyOperaion.getOperationType())
                .nextApplicableMonth(periodicalMoneyOperaion.getNextApplicableMonth())
                .operationCategory(operationCategoryService.from(periodicalMoneyOperaion.getOperationCategory()))
                .build();
    }

    public List<PeriodicalMoneyOperaion> getOperationsForMonth(Month accountingMonth) {
        QPeriodicalMoneyOperaion operation = QPeriodicalMoneyOperaion.periodicalMoneyOperaion;
        List<PeriodicalMoneyOperaion> ret = new ArrayList<>();
        var operations = operation.nextApplicableMonth.eq(accountingMonth);
        repository.findAll(operations).forEach(ret::add);
        return ret;
    }

    public void saveAll(List<PeriodicalMoneyOperaion> operationList) {
        repository.saveAll(operationList);
    }
}
