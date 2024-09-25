package com.example.devicesservice.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JsonConverterImpl implements JsonConverter {

    private final ObjectMapper objectMapper;

    @Override
    public <T> T fromJson(String json, Class<T> clazz) {
        return objectMapper.convertValue(json, clazz);
    }

    @Override
    public String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
