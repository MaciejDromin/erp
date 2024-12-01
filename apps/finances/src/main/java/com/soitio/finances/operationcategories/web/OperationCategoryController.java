package com.soitio.finances.operationcategories.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.soitio.commons.dependency.model.DependencyCheckResponse;
import com.soitio.commons.dependency.model.Dependent;
import com.soitio.finances.operationcategories.application.OperationCategoryService;
import com.soitio.finances.operationcategories.domain.dto.OperationCategoryCreationDto;
import com.soitio.finances.operationcategories.domain.dto.OperationCategoryDto;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/operation-category")
@RequiredArgsConstructor
public class OperationCategoryController {

    private final OperationCategoryService operationCategoryService;

    @GetMapping
    public Page<OperationCategoryDto> getOperationCategories(@RequestParam Map<String, String> queryParams) {
        return operationCategoryService.getPage(queryParams);
    }

    @GetMapping("/{operationCategoryId}")
    public OperationCategoryDto getOperationCategory(@PathVariable("operationCategoryId") String id) {
        return operationCategoryService.getOperationCategory(id);
    }

    @PostMapping
    public void create(@RequestBody OperationCategoryCreationDto creation) {
        operationCategoryService.create(creation);
    }

    @DeleteMapping
    public DependencyCheckResponse delete(@RequestBody Set<String> ids) {
        return operationCategoryService.delete(Dependent.FINANCES_CATEGORY, ids);
    }

    @PatchMapping("/{categoryId}")
    public void update(@PathVariable("categoryId") String id, @RequestBody JsonNode node) {
        operationCategoryService.update(Dependent.FINANCES_CATEGORY, id, node);
    }

}

