package pl.mlisowski.finances.objectvalues.web;

import java.util.List;
import java.util.Map;
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
import pl.mlisowski.finances.objectvalues.application.ObjectValueService;
import pl.mlisowski.finances.objectvalues.domain.ObjectType;
import pl.mlisowski.finances.objectvalues.domain.dto.ObjectValueCreationDto;
import pl.mlisowski.finances.objectvalues.domain.dto.ObjectValueDto;
import pl.mlisowski.finances.objectvalues.domain.dto.TotalObjectsValueDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/finances/object-value")
public class ObjectValueController {

    private final ObjectValueService objectValueService;

    @GetMapping
    public Page<ObjectValueDto> getPage(@PageableDefault(size = 20) Pageable pageable,
                                        @RequestParam("objectType") ObjectType objectType) {
        return objectValueService.getPage(pageable, objectType);
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

}
