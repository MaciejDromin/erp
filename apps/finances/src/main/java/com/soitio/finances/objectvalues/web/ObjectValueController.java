package com.soitio.finances.objectvalues.web;

import com.soitio.commons.dependency.model.DependencyCheckResponse;
import com.soitio.commons.dependency.model.Dependent;
import com.soitio.commons.models.dto.finances.ObjectValueDto;
import com.soitio.commons.models.dto.finances.TopItemByCategoryDto;
import com.soitio.finances.objectvalues.application.ObjectValueService;
import com.soitio.finances.objectvalues.domain.ObjectType;
import com.soitio.finances.objectvalues.domain.dto.ObjectValueCreationDto;
import com.soitio.finances.objectvalues.domain.dto.TotalObjectsValueDto;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/finances/object-value")
public class ObjectValueController {

    private final ObjectValueService objectValueService;

    @GetMapping
    public Page<ObjectValueDto> getPage(@PageableDefault(size = 20) Pageable pageable,
                                        @RequestParam("objectType") ObjectType objectType,
                                        @RequestParam(value = "objectIds", required = false) Set<String> objectIds) {
        return objectValueService.getPage(pageable, objectType, objectIds);
    }

    @PostMapping
    public void create(@RequestBody ObjectValueCreationDto creation) {
        objectValueService.create(creation);
    }

    @PostMapping("/total-value")
    public TotalObjectsValueDto totalValue(@RequestBody Map<String, Integer> quantityByObjectMap,
                                           @RequestParam("objectType") ObjectType objectType) {
        return objectValueService.totalValue(quantityByObjectMap, objectType);
    }

    @GetMapping("/object-ids")
    public List<String> allObjectIds(@RequestParam("objectType") ObjectType objectType) {
        return objectValueService.allObjectIds(objectType);
    }

    @PostMapping("/top")
    public TopItemByCategoryDto findTopByObjectIdsIn(@RequestBody Set<String> value) {
        return objectValueService.findTopByObjectIdsIn(value);
    }

    @DeleteMapping
    public DependencyCheckResponse delete(@RequestBody Set<String> ids) {
        return objectValueService.delete(Dependent.FINANCES_PERIODICAL, ids);
    }

}
