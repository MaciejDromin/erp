package com.soitio.reports.service.data;

import java.util.Map;
import java.util.function.Supplier;

public interface ValueMappable {

     Map<String, Supplier<Object>> getFieldMap();

}
