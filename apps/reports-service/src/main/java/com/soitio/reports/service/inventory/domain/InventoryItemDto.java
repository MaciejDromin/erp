package com.soitio.reports.service.inventory.domain;

import com.soitio.reports.service.data.ValueMappable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

@Builder
@AllArgsConstructor
@Getter
public class InventoryItemDto implements ValueMappable {

    private String id;
    private String name;
    private Integer quantity;
    private ItemUnit unit;
    private Set<CategoryDto> categories;

    @Override
    public Map<String, Supplier<Object>> getFieldMap() {
        return Map.of(
            "id", this::getId,
            "name", this::getName,
            "quantity", this::getQuantity,
            "unit", this::getUnit,
            "categories", this::getCategories
        );
    }
}
