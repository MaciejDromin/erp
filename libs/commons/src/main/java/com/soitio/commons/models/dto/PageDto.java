package com.soitio.commons.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor(staticName = "of")
@Getter
@NoArgsConstructor
public class PageDto<T> {

    @Builder.Default
    private List<T> content = new ArrayList<>();
    private Integer totalPages;

}
