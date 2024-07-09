package com.soitio.widgets.startup.runtime.domain;

import com.soitio.widgets.common.domain.FilterType;

import java.util.List;
import java.util.Set;

public interface IFilter {

    /**
     * Filter name
     * */
    String name();

    /**
     * Filter dependency
     * */
    Set<String> dependsOn();

     /**
     * Filter type
     * */
    FilterType filterType();

     /**
     * Filter min value
     * */
    Object min();

     /**
     * Filter max value
     * */
    Object max();

     /**
     * Filter list of options
     * */
    List<Object> options();

     /**
     * Filter is mandatory
     * */
    boolean mandatory();

     /**
     * Filter is dynamic
     * */
    boolean dynamic();

     /**
     * Filter datasource
     * */
    String datasource();

}
