package com.example.devicesservice.exceptions;

public class NotFoundException extends BaseException {

    public NotFoundException(String message) {
        super(404, "404000", "NOT_FOUND", message);
    }

}
