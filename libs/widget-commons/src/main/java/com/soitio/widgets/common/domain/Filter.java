package com.soitio.widgets.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class Filter {

    private String name;
    private Set<String> dependsOn;
    private FilterType filterType;
    private Object min;
    private Object max;
    private List<Object> options;
    private boolean mandatory;
    private boolean dynamic;
    private String datasource;

}
