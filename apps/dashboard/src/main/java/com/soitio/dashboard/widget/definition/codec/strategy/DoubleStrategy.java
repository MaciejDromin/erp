package com.soitio.dashboard.widget.definition.codec.strategy;

import com.soitio.widgets.common.domain.data.AnyPossibleValueWrapper;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;

@ApplicationScoped
public class DoubleStrategy implements MapperStrategy<Double> {

    @Override
    public void write(BsonWriter bsonWriter, AnyPossibleValueWrapper anyPossibleValueWrapper) {
        bsonWriter.writeDouble((Double) anyPossibleValueWrapper.value());
    }

    @Override
    public AnyPossibleValueWrapper read(BsonReader reader) {
        return new AnyPossibleValueWrapper(reader.readDouble());
    }

    @Override
    public Class<Double> getSupportedClass() {
        return Double.class;
    }

    @Override
    public BsonType getBsonType() {
        return BsonType.DOUBLE;
    }

}
