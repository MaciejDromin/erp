package com.soitio.inventory.item.domain.dto;

import com.soitio.commons.models.inventory.ItemUnit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.util.Set;

@Builder
@AllArgsConstructor
@Getter
public class ItemCreationDto {

    private String name;
    private Integer quantity;
    private ItemUnit unit;
    private Set<String> categoryIds;

}
