package pl.mlisowski.finances.operationcategories.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.mlisowski.finances.operationcategories.application.OperationCategoryService;
import pl.mlisowski.finances.operationcategories.domain.dto.OperationCategoryCreationDto;
import pl.mlisowski.finances.operationcategories.domain.dto.OperationCategoryDto;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/finances/operation-category")
@RequiredArgsConstructor
public class OperationCategoryController {

    private final OperationCategoryService operationCategoryService;

    @GetMapping
//    public Page<OperationCategoryDto> getOperationCategories(@PageableDefault(size = 20) Pageable pageable) {
    public Page<OperationCategoryDto> getOperationCategories(@RequestParam Map<String, String> queryParams) {
        log.info("ML queryParams --- {}", queryParams);
        return operationCategoryService.getPage(Pageable.ofSize(20).withPage(Integer.parseInt(queryParams.get("page"))));
    }

    @PostMapping
    public void create(@RequestBody OperationCategoryCreationDto creation) {
        operationCategoryService.create(creation);
    }

}

