package com.soitio.widgets.common.domain.data;

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
public class Dataset {

    private String label;
    private List<Double> data;
    private List<Rgba> backgroundColor;
    private List<Rgba> borderColor;
    private int borderWidth;

}
