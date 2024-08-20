package com.soitio.dashboard.widget.definition.codec.strategy;

import com.soitio.widgets.common.domain.data.AnyPossibleValueWrapper;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;

@ApplicationScoped
public class IntegerStrategy implements MapperStrategy<Integer> {

    @Override
    public void write(BsonWriter bsonWriter, AnyPossibleValueWrapper anyPossibleValueWrapper) {
        bsonWriter.writeInt32((Integer) anyPossibleValueWrapper.value());
    }

    @Override
    public AnyPossibleValueWrapper read(BsonReader reader) {
        return new AnyPossibleValueWrapper(reader.readInt32());
    }

    @Override
    public Class<Integer> getSupportedClass() {
        return Integer.class;
    }

    @Override
    public BsonType getBsonType() {
        return BsonType.INT32;
    }

}
