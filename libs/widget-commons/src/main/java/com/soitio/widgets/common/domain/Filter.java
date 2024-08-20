package com.soitio.widgets.common.domain;

import com.soitio.widgets.common.domain.data.AnyPossibleValueWrapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class Filter {

    private String name;
    private List<String> dependsOn;
    private FilterType filterType;
    private AnyPossibleValueWrapper min;
    private AnyPossibleValueWrapper max;
    private List<String> options;
    private boolean mandatory;
    private String datasource;

}
