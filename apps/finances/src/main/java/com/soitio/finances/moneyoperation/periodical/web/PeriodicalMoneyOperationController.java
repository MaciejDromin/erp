package com.soitio.finances.moneyoperation.periodical.web;

import com.fasterxml.jackson.databind.JsonNode;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/money-operation/periodical")
@RequiredArgsConstructor
public class PeriodicalMoneyOperationController {

    private final PeriodicalMoneyOperationService periodicalMoneyOperationService;

    @GetMapping
    public Page<PeriodicalMoneyOperationDto> getMoneyOperations(@PageableDefault(size = 20) Pageable pageable,
                                                                @RequestHeader("X-OrgId") String orgId) {
        return periodicalMoneyOperationService.getPage(pageable, orgId);
    }

    @PostMapping
    public void registerMoneyOperation(@RequestBody PeriodicalMoneyOperationCreationDto creation,
                                       @RequestHeader("X-OrgId") String orgId) {
        periodicalMoneyOperationService.create(creation, orgId);
    }

    @GetMapping("/{periodicalMoneyOperationId}")
    public PeriodicalMoneyOperationDto getSingle(@PathVariable("periodicalMoneyOperationId") String id,
                                                 @RequestHeader("X-OrgId") String orgId) {
        return periodicalMoneyOperationService.getPeriodicalOperation(id, orgId);
    }

    @DeleteMapping
    public DependencyCheckResponse delete(@RequestBody Set<String> ids,
                                          @RequestHeader("X-OrgId") String orgId) {
        return periodicalMoneyOperationService.delete(Dependent.FINANCES_PERIODICAL, ids, orgId);
    }

    @PatchMapping("/{periodicalMoneyOperationId}")
    public void updateSinglePeriodicalMoneyOperation(@PathVariable("periodicalMoneyOperationId") String id,
                                                     @RequestBody JsonNode node,
                                                     @RequestHeader("X-OrgId") String orgId) {
        periodicalMoneyOperationService.update(Dependent.FINANCES_PERIODICAL, id, node, orgId);
    }

}
