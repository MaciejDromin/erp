package com.soitio.finances.operationcategories.web;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.soitio.finances.operationcategories.application.OperationCategoryService;
import com.soitio.finances.operationcategories.domain.OperationCategory;
import com.soitio.finances.operationcategories.domain.dto.OperationCategoryCreationDto;
import com.soitio.finances.operationcategories.domain.dto.OperationCategoryDto;

@RestController
@RequestMapping("/finances/operation-category")
@RequiredArgsConstructor
public class OperationCategoryController {

    private final OperationCategoryService operationCategoryService;

    @GetMapping
    public Page<OperationCategoryDto> getOperationCategories(@RequestParam Map<String, String> queryParams) {
        return operationCategoryService.getPage(queryParams);
    }

    @PostMapping
    public void create(@RequestBody OperationCategoryCreationDto creation) {
        operationCategoryService.create(creation);
    }

}

