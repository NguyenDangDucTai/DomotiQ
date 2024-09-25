package com.example.devicesservice.utils;

import org.springframework.stereotype.Service;

@Service
public interface JsonConverter {

    <T> T fromJson(String json, Class<T> clazz);
    String toJson(Object object);

}
