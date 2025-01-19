package com.soitio.finances.moneyoperation.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.soitio.commons.dependency.model.DependencyCheckResponse;
import com.soitio.commons.dependency.model.Dependent;
import com.soitio.finances.moneyoperation.application.MoneyOperationService;
import com.soitio.finances.moneyoperation.domain.dto.MoneyOperationBalanceDto;
import com.soitio.finances.moneyoperation.domain.dto.MoneyOperationCreationDto;
import com.soitio.finances.moneyoperation.domain.dto.MoneyOperationDto;
import java.time.Month;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/money-operation")
@RequiredArgsConstructor
public class MoneyOperationController {

    private final MoneyOperationService operationService;

    @GetMapping
    public Page<MoneyOperationDto> getMoneyOpeations(@PageableDefault(size = 20) Pageable pageable,
                                                     @RequestHeader("X-OrgId") String orgId) {
        return operationService.getPage(pageable, orgId);
    }

    @GetMapping("/{moneyOperationId}")
    public MoneyOperationDto getMoneyOperation(@PathVariable("moneyOperationId") String id,
                                               @RequestHeader("X-OrgId") String orgId) {
        return operationService.getMoneyOperation(id, orgId);
    }

    @PostMapping
    public void registerMoneyOperation(@RequestBody MoneyOperationCreationDto creation,
                                       @RequestHeader("X-OrgId") String orgId) {
        operationService.create(creation, orgId);
    }

    @GetMapping("/balance")
    public List<MoneyOperationBalanceDto> getOperationsForBalance(@RequestParam("balanceYear") int balanceYear,
                                                                  @RequestParam(value = "balanceMonth", required = false) Month month,
                                                                  @RequestHeader("X-OrgId") String orgId) {
        return operationService.getForBalance(balanceYear, month, orgId);
    }

    @DeleteMapping
    public DependencyCheckResponse delete(@RequestBody Set<String> ids,
                                          @RequestHeader("X-OrgId") String orgId) {
        return operationService.delete(Dependent.FINANCES_MONEY_OPERATION, ids, orgId);
    }

    @PatchMapping("/{moneyOperationId}")
    public void updateSingleMoneyOperation(@PathVariable("moneyOperationId") String id,
                                           @RequestBody JsonNode node,
                                           @RequestHeader("X-OrgId") String orgId) {
        operationService.update(Dependent.FINANCES_MONEY_OPERATION, id, node, orgId);
    }

}
