package com.soitio.widgets.finances.domain;

import com.soitio.commons.models.dto.finances.TopItemByCategoryDto;
import com.soitio.commons.models.dto.inventory.category.CategoryDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class TopItemCategoryWrapper {

    private final CategoryDto category;
    private final TopItemByCategoryDto topItem;

}
