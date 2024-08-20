package com.soitio.dashboard.widget.definition.codec.strategy;

import com.soitio.widgets.common.domain.data.AnyPossibleValueWrapper;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;

@ApplicationScoped
public class StringStrategy implements MapperStrategy<String> {

    @Override
    public void write(BsonWriter bsonWriter, AnyPossibleValueWrapper anyPossibleValueWrapper) {
        bsonWriter.writeString((String) anyPossibleValueWrapper.value());
    }

    @Override
    public AnyPossibleValueWrapper read(BsonReader reader) {
        return new AnyPossibleValueWrapper(reader.readString());
    }

    @Override
    public Class<String> getSupportedClass() {
        return String.class;
    }

    @Override
    public BsonType getBsonType() {
        return BsonType.STRING;
    }

}
