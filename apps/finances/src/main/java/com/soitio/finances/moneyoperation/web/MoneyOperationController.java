package com.soitio.finances.moneyoperation.web;

import com.soitio.finances.moneyoperation.application.MoneyOperationService;
import com.soitio.finances.moneyoperation.domain.dto.MoneyOperationBalanceDto;
import com.soitio.finances.moneyoperation.domain.dto.MoneyOperationCreationDto;
import com.soitio.finances.moneyoperation.domain.dto.MoneyOperationDto;
import java.time.Month;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/finances/money-operation")
@RequiredArgsConstructor
public class MoneyOperationController {

    private final MoneyOperationService operationService;

    @GetMapping
    public Page<MoneyOperationDto> getMoneyOpeations(@PageableDefault(size = 20) Pageable pageable) {
        return operationService.getPage(pageable);
    }

    @PostMapping
    public void registerMoneyOperation(@RequestBody MoneyOperationCreationDto creation) {
        operationService.create(creation);
    }

    @GetMapping("/balance")
    public List<MoneyOperationBalanceDto> getOperationsForBalance(@RequestParam("balanceYear") int balanceYear,
                                                                  @RequestParam(value = "balanceMonth", required = false) Month month) {
        return operationService.getForBalance(balanceYear, month);
    }

}
