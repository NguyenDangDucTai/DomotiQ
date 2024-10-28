package com.example.devicesservice.exceptions;

public class AccessDeniedException extends BaseException {
    public AccessDeniedException(String message) {
        super(403, "403001", "ACCESS_DENIED", message);
    }
}
