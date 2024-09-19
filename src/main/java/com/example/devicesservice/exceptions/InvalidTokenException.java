package com.example.devicesservice.exceptions;

public class InvalidTokenException extends BaseException {

    public InvalidTokenException(String message) {
        super(401, "401002", "INVALID_TOKEN", message);
    }

}
