package com.soitio.planner.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor(staticName = "of")
@Getter
public class PageDto<T> {

    @Builder.Default
    private List<T> content = new ArrayList<>();
    private Integer totalPages;

}
