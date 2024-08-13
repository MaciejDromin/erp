package com.soitio.finances.receipt.listener.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ConsumerConfig {

    private final ObjectMapper objectMapper;

    @Bean
    public Jackson2JsonMessageConverter jackson2Converter() {
        return new Jackson2JsonMessageConverter(objectMapper);
    }


}
