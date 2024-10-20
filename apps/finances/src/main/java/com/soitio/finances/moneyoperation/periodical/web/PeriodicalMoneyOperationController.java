package com.soitio.finances.moneyoperation.periodical.web;

import com.soitio.commons.dependency.model.DependencyCheckResponse;
import com.soitio.commons.dependency.model.Dependent;
import com.soitio.finances.moneyoperation.periodical.application.PeriodicalMoneyOperationService;
import com.soitio.finances.moneyoperation.periodical.domain.dto.PeriodicalMoneyOperationCreationDto;
import com.soitio.finances.moneyoperation.periodical.domain.dto.PeriodicalMoneyOperationDto;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/finances/money-operation/periodical")
@RequiredArgsConstructor
public class PeriodicalMoneyOperationController {

    private final PeriodicalMoneyOperationService periodicalMoneyOperationService;

    @GetMapping
    public Page<PeriodicalMoneyOperationDto> getMoneyOpeations(@PageableDefault(size = 20) Pageable pageable) {
        return periodicalMoneyOperationService.getPage(pageable);
    }

    @PostMapping
    public void registerMoneyOperation(@RequestBody PeriodicalMoneyOperationCreationDto creation) {
        periodicalMoneyOperationService.create(creation);
    }

    @DeleteMapping
    public DependencyCheckResponse delete(@RequestBody Set<String> ids) {
        return periodicalMoneyOperationService.delete(Dependent.FINANCES_PERIODICAL, ids);
    }

}
