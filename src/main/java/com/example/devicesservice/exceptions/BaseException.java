package com.example.devicesservice.exceptions;

import lombok.Getter;

import java.util.Map;

@Getter
public class BaseException extends RuntimeException {

    private final Integer status;
    private final String code;
    private final String type;
    private Map<String, Object> extraError;

    public BaseException(Integer status, String code, String type, String message) {
        super(message);
        this.status = status;
        this.code = code;
        this.type = type;
    }

    public BaseException(Integer status, String code, String type, String message, Map<String, Object> extraError) {
        this(status, code, type, message);
        this.extraError = extraError;
    }

}
