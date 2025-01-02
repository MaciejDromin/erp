package com.soitio.api.gateway.codec;

import com.soitio.api.gateway.codec.strategy.CodecStrategyProvider;
import com.soitio.api.gateway.domain.ValueWrapper;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

public class ValueWrapperCodecProvider implements CodecProvider {

    private final CodecStrategyProvider strategyProvider;

    public ValueWrapperCodecProvider(CodecStrategyProvider strategyProvider) {
        this.strategyProvider = strategyProvider;
    }

    @Override
    public <T> Codec<T> get(Class<T> clazz, CodecRegistry codecRegistry) {
        if (clazz.equals(ValueWrapper.class)) {
            return (Codec<T>) new ValueWrapperCodec(strategyProvider);
        }
        return null;
    }
}
