package com.soitio.widgets.startup.runtime.domain;

import com.soitio.widgets.common.domain.FilterType;
import com.soitio.widgets.common.domain.data.AnyPossibleValueWrapper;
import com.soitio.widgets.startup.runtime.converters.AnyPossibleValueConverter;
import io.smallrye.config.WithConverter;
import java.util.List;
import java.util.Optional;

public interface IFilter {

    /**
     * Filter name
     * */
    String name();

    /**
     * Filter dependency
     * */
    Optional<List<String>> dependsOn();

    /**
     * Filter type
     * */
    FilterType filterType();

    /**
     * Filter min value
     * */
    @WithConverter(AnyPossibleValueConverter.class)
    AnyPossibleValueWrapper min();

    /**
     * Filter max value
     * */
    @WithConverter(AnyPossibleValueConverter.class)
    AnyPossibleValueWrapper max();

    /**
     * Filter list of options
     * */
    Optional<List<String>> options();

    /**
     * Filter is mandatory
     * */
    boolean mandatory();

    /**
     * Filter datasource
     * */
    Optional<String> datasource();

}
