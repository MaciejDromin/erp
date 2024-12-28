package com.soitio.api.gateway.codec;

import com.soitio.api.gateway.codec.strategy.CodecStrategyProvider;
import com.soitio.api.gateway.domain.ValueWrapper;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class ValueWrapperCodec implements Codec<ValueWrapper> {

    private final CodecStrategyProvider strategyProvider;

    public ValueWrapperCodec(CodecStrategyProvider strategyProvider) {
        this.strategyProvider = strategyProvider;
    }

    @Override
    public ValueWrapper decode(BsonReader bsonReader, DecoderContext decoderContext) {
        return strategyProvider.getMapper(bsonReader.getCurrentBsonType()).read(bsonReader);
    }

    @Override
    public void encode(BsonWriter bsonWriter, ValueWrapper valueWrapper, EncoderContext encoderContext) {
        strategyProvider.getMapper(valueWrapper.value()).write(bsonWriter, valueWrapper);
    }

    @Override
    public Class<ValueWrapper> getEncoderClass() {
        return ValueWrapper.class;
    }

}
