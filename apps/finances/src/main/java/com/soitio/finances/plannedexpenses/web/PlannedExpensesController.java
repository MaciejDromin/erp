package com.soitio.finances.plannedexpenses.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.soitio.commons.dependency.model.DependencyCheckResponse;
import com.soitio.commons.dependency.model.Dependent;
import com.soitio.commons.models.dto.finances.PlannedExpensesDto;
import com.soitio.finances.plannedexpenses.application.PlannedExpensesService;
import com.soitio.finances.plannedexpenses.domain.dto.PlannedExpensesCompletionDto;
import com.soitio.finances.plannedexpenses.domain.dto.PlannedExpensesCreationDto;
import java.util.Map;
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
@RequestMapping("/planned-expenses")
@RequiredArgsConstructor
public class PlannedExpensesController {

    private final PlannedExpensesService service;

    @GetMapping
    public Page<PlannedExpensesDto> getAll(@PageableDefault(size = 20) Pageable pageable,
                                           @RequestParam Map<String, String> params,
                                           @RequestHeader("X-OrgId") String orgId) {
        return service.getAll(pageable, params, orgId);
    }

    @GetMapping("/{plannedExpenseId}")
    public PlannedExpensesDto getPlannedExpense(@PathVariable("plannedExpenseId") String id,
                                                @RequestHeader("X-OrgId") String orgId) {
        return service.getPlannedExpense(id, orgId);
    }

    @PostMapping
    public void create(@RequestBody PlannedExpensesCreationDto creation,
                       @RequestHeader("X-OrgId") String orgId) {
        service.create(creation, orgId);
    }

    @PatchMapping("/{plannedExpenseId}/abandon")
    public void abandon(@PathVariable String plannedExpenseId,
                        @RequestHeader("X-OrgId") String orgId) {
        service.abandon(plannedExpenseId, orgId);
    }

    @PatchMapping("/{plannedExpenseId}/complete")
    public void complete(@PathVariable String plannedExpenseId, @RequestBody PlannedExpensesCompletionDto completion,
                         @RequestHeader("X-OrgId") String orgId) {
        service.complete(plannedExpenseId, completion, orgId);
    }

    @DeleteMapping
    public DependencyCheckResponse delete(@RequestBody Set<String> ids,
                                          @RequestHeader("X-OrgId") String orgId) {
        return service.delete(Dependent.FINANCES_PLANNED_EXPENSES, ids, orgId);
    }

    @PatchMapping("/{plannedExpensesId}")
    public void updateSinglePlannedExpense(@PathVariable("plannedExpensesId") String id, @RequestBody JsonNode node,
                                           @RequestHeader("X-OrgId") String orgId) {
        service.update(Dependent.FINANCES_PLANNED_EXPENSES, id, node, orgId);
    }

}
