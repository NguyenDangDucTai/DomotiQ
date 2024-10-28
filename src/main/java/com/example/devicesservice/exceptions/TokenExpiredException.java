package com.example.devicesservice.exceptions;

public class TokenExpiredException extends BaseException {
    public TokenExpiredException(String message) {
        super(401, "401003", "TOKEN_EXPIRED", message);
    }
}
