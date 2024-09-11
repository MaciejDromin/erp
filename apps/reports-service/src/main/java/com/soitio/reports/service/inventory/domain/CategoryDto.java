package com.soitio.reports.service.inventory.domain;

import com.soitio.reports.service.data.ValueMappable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.util.Map;
import java.util.function.Supplier;

@Builder
@AllArgsConstructor
@Getter
public class CategoryDto implements ValueMappable {

    private String id;
    private String name;

    @Override
    public Map<String, Supplier<Object>> getFieldMap() {
        return Map.of(
                "id", this::getId,
                "name", this::getName
        );
    }
}
