package com.soitio.analytics.common.utils;

import java.util.function.Consumer;
import java.util.function.Supplier;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FunctionUtils {

    public static <T> void calculateAndSet(Consumer<T> setterFunc, Supplier<T> calculator) {
        setterFunc.accept(calculator.get());
    }

}
