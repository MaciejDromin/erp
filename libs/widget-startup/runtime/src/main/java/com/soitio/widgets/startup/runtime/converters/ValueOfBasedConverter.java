package com.soitio.widgets.startup.runtime.converters;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ValueOfBasedConverter<T> {

    public static final String VALUE_OF = "valueOf";
    private final Method method;
    private final Class<T> clazz;

    private ValueOfBasedConverter(Class<T> clazz, Method method) {
        this.clazz = clazz;
        this.method = method;
    }

    public static <T> ValueOfBasedConverter<T> getIfEligible(Class<T> clazz) {
        try {
            Method method = clazz.getMethod(VALUE_OF, String.class);
            if (Modifier.isStatic(method.getModifiers())) {
                return new ValueOfBasedConverter<>(clazz, method);
            }
            throw new IllegalStateException("Method '%s' of class '%s' is not static and cannot be called"
                    .formatted(VALUE_OF, clazz.getName()));
        } catch (NoSuchMethodException var2) {
            throw new IllegalStateException("Class '%s' does not have a static public '%s' method"
                    .formatted(clazz.getName(), VALUE_OF));
        }
    }

    public T fromString(String input) throws IllegalArgumentException {
        try {
            return this.clazz.cast(this.method.invoke(null, input));
        } catch (InvocationTargetException | IllegalAccessException err) {
            if (err.getCause() != null) {
                throw new IllegalArgumentException(err.getCause());
            }
            throw new IllegalArgumentException(err);
        }
    }

}
