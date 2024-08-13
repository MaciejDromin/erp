package com.soitio.finances.moneyoperation.periodical.web;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.soitio.finances.moneyoperation.periodical.application.PeriodicalMoneyOperationService;
import com.soitio.finances.moneyoperation.periodical.domain.dto.PeriodicalMoneyOperationCreationDto;
import com.soitio.finances.moneyoperation.periodical.domain.dto.PeriodicalMoneyOperationDto;

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

}
