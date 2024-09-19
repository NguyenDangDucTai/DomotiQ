package com.example.devicesservice.exceptions;

public class UnauthorizedException extends BaseException {

    public UnauthorizedException(String message) {
        super(401, "401000", "UNAUTHORIZED", message);
    }

}
