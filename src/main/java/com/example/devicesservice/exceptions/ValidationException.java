package com.example.devicesservice.exceptions;

import java.util.Map;

public class ValidationException extends BaseException {

    public ValidationException(Map<String, Object> extraError) {
        super(400, "400000", "INVALID_REQUEST", "Invalid request", extraError);
    }

}
