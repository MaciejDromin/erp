package com.soitio.widgets.finances.domain;

import com.soitio.commons.models.dto.inventory.category.CategoryDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class CategoryWrapper<T> {

    private final CategoryDto category;
    private final T wrapped;

}
