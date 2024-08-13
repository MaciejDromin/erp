package com.soitio.finances.plannedexpenses.web;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.soitio.finances.plannedexpenses.application.PlannedExpensesService;
import com.soitio.finances.plannedexpenses.domain.dto.PlannedExpensesCompletionDto;
import com.soitio.finances.plannedexpenses.domain.dto.PlannedExpensesCreationDto;
import com.soitio.finances.plannedexpenses.domain.dto.PlannedExpensesDto;

@RestController
@RequestMapping("/finances/planned-expenses")
@RequiredArgsConstructor
public class PlannedExpensesController {

    private final PlannedExpensesService service;

    @GetMapping
    public Page<PlannedExpensesDto> getAll(@PageableDefault(size = 20) Pageable pageable) {
        return service.getAll(pageable);
    }

    @PostMapping
    public void create(@RequestBody PlannedExpensesCreationDto creation) {
        service.create(creation);
    }

    @PatchMapping("/{plannedExpenseId}/abandon")
    public void abandon(@PathVariable String plannedExpenseId) {
        service.abandon(plannedExpenseId);
    }

    @PatchMapping("/{plannedExpenseId}/complete")
    public void complete(@PathVariable String plannedExpenseId, @RequestBody PlannedExpensesCompletionDto completion) {
        service.complete(plannedExpenseId, completion);
    }

}
