package com.soitio.api.gateway.codec.strategy.impl;

import com.soitio.api.gateway.codec.strategy.CodecStrategy;
import com.soitio.api.gateway.codec.strategy.CodecStrategyProvider;
import com.soitio.api.gateway.domain.ValueWrapper;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ArrayStrategy implements CodecStrategy<List<Object>> {

    private final CodecStrategyProvider strategyProvider;

    public ArrayStrategy(CodecStrategyProvider strategyProvider) {
        this.strategyProvider = strategyProvider;
    }

    @Override
    public void write(BsonWriter bsonWriter, ValueWrapper valueWrapper) {
        List<?> list = (List<?>) valueWrapper.value();
        bsonWriter.writeStartArray();
        for (Object o : list) strategyProvider.getMapper(o).write(bsonWriter, new ValueWrapper(o));
        bsonWriter.writeEndArray();
    }

    @Override
    public List<Object> readBsonValue(BsonReader reader) {
        List<Object> ret = new ArrayList<>();
        reader.readStartArray();
        BsonType type = reader.readBsonType();
        while (type != BsonType.END_OF_DOCUMENT) {
            ret.add(strategyProvider.getMapper(type).readBsonValue(reader));
            type = reader.readBsonType();
        }
        reader.readEndArray();
        return ret;
    }

    @Override
    public Class<List<Object>> getSupportedClass() {
        return (Class<List<Object>>)(Class<?>)List.class;
    }

    @Override
    public BsonType getBsonType() {
        return BsonType.ARRAY;
    }
}
