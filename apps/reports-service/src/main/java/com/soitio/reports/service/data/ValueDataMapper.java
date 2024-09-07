package com.soitio.reports.service.data;

import com.soitio.reports.ListValue;
import com.soitio.reports.Struct;
import com.soitio.reports.Value;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public final class ValueDataMapper {

    private ValueDataMapper() {
        throw new AssertionError("Utility class");
    }

    public static <T> Map<String, Value> mapToValueMap(List<T> data) {
        Map<String, Value> dataMap = new HashMap<>();
        dataMap.put("data", Value.newBuilder()
                .setListValue(mapToListValue(data.stream()
                        .map(ValueDataMapper::mapToValue)
                        .toList()))
                .build());
        return dataMap;
    }

    public static ListValue mapToListValue(Iterable<Value> values) {
        return ListValue.newBuilder()
                .addAllValues(values)
                .build();
    }

    public static <T> Value objToValue(Map<String, Supplier<T>> fieldMap) {
        Struct struct = Struct.newBuilder()
                .putAllFields(fieldMap.entrySet().stream()
                        .collect(Collectors.toMap(Map.Entry::getKey, e -> mapToValue(e.getValue().get()))))
                .build();
        return Value.newBuilder()
                .setStructValue(struct)
                .build();
    }

    public static <T> Value mapToValue(T obj) {
        if (obj instanceof ValueMappable val) return objToValue(val.getFieldMap());
        if (obj instanceof Collection<?> itr) return Value.newBuilder().setListValue(mapList(itr)).build();
        return createMappers(obj).stream()
                .filter(ValueMapper::isApplicable)
                .findFirst()
                .map(ValueMapper::map)
                .orElseGet(() -> Value.newBuilder()
                .setStringValue(String.valueOf(obj))
                .build());
    }

    private static <T> Set<ValueMapper<T>> createMappers(T obj) {
        return Set.of(
                ValueMapper.of(obj instanceof Double, obj, v -> Value.newBuilder().setDoubleValue((Double) v).build()),
                ValueMapper.of(obj instanceof Integer, obj, v -> Value.newBuilder().setIntValue((Integer) v).build()),
                ValueMapper.of(obj instanceof Boolean, obj, v -> Value.newBuilder().setBoolValue((Boolean) v).build())
        );
    }

    private static <T> ListValue mapList(Collection<T> obj) {
        return ListValue.newBuilder()
                .addAllValues(obj.stream()
                        .map(ValueDataMapper::mapToValue)
                        .toList())
                .build();
    }

    private record ValueMapper<T>(boolean isApplicable, T objToMap, Function<T, Value> mappingFunction) {

        public static <T> ValueMapper<T> of(boolean isApplicable, T objToMap, Function<T, Value> mappingFunction) {
                return new ValueMapper<>(isApplicable, objToMap, mappingFunction);
            }

            public Value map() {
                return this.mappingFunction.apply(this.objToMap);
            }

        }

}
