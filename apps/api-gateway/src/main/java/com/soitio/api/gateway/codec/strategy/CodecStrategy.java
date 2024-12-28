package com.soitio.api.gateway.codec.strategy;

import com.soitio.api.gateway.domain.ValueWrapper;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;

import java.util.function.Supplier;

public interface CodecStrategy<T> {

    void write(BsonWriter bsonWriter, ValueWrapper valueWrapper);

    default ValueWrapper read(BsonReader reader) {
        return new ValueWrapper(readBsonValue(reader));
    }

    T readBsonValue(BsonReader reader);

    Class<T> getSupportedClass();

    BsonType getBsonType();

    default boolean isApplicable(Object o) {
        Class<T> clazz = getSupportedClass();
        if (o == null && getSupportedClass() == Void.class) return true;
        if (clazz.isInstance(o)) {
            return true;
        }
        return getBsonType() == o;
    }

}
