package com.soitio.widgets.startup.runtime.domain;

import com.soitio.widgets.common.domain.FilterType;

import java.util.Set;

public interface IFilter {

    String name();
    Set<String> dependsOn();
    FilterType filterType();
    Object min();
    Object max();
    Object options();
    boolean mandatory();
    boolean dynamic();
    String datasource();

}
