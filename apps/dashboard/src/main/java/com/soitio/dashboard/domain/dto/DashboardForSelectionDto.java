package com.soitio.dashboard.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class DashboardForSelectionDto {

    private String id;
    private String name;
    private boolean defaultForType;

}
