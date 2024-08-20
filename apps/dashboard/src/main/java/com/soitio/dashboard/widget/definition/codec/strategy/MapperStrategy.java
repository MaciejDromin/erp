package com.soitio.dashboard.widget.definition.codec.strategy;

import com.soitio.widgets.common.domain.data.AnyPossibleValueWrapper;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;

public interface MapperStrategy<T> {

    void write(BsonWriter bsonWriter, AnyPossibleValueWrapper anyPossibleValueWrapper);

    AnyPossibleValueWrapper read(BsonReader reader);

    Class<T> getSupportedClass();

    BsonType getBsonType();

    default boolean isApplicable(Object o) {
        Class<T> clazz = getSupportedClass();
        if (clazz.isInstance(o)) {
            return true;
        }
        return getBsonType() == o;
    }

}
