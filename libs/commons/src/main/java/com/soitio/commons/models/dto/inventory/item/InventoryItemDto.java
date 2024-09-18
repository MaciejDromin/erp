package com.soitio.commons.models.dto.inventory.item;

import com.soitio.commons.models.dto.inventory.category.CategoryDto;
import com.soitio.commons.models.inventory.ItemUnit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Builder
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class InventoryItemDto {

    private String id;
    private String name;
    private Integer quantity;
    private ItemUnit unit;
    private Set<CategoryDto> categories;

}
