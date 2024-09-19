package com.example.devicesservice.exceptions.handler;

import com.example.devicesservice.dtos.ErrorResponse;
import com.example.devicesservice.exceptions.BaseException;
import com.example.devicesservice.mappers.ErrorMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private final ErrorMapper errorMapper;

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(BaseException e) {
        return ResponseEntity.status(e.getStatus()).body(errorMapper.toErrorResponse(e));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(MethodArgumentNotValidException e) {
        Map<String, Object> extra = new HashMap<>();
        e.getFieldErrors().forEach(violation -> extra.put(violation.getField(), violation.getDefaultMessage()));

        ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .code("400000")
                .type("INVALID_REQUEST")
                .message("Invalid request")
                .extraError(extra)
                .build();

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // Sort field not found
    @ExceptionHandler(PropertyReferenceException.class)
    public ResponseEntity<ErrorResponse> handlePropertyReferenceException(PropertyReferenceException ex) {
        ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .code("400000")
                .type("INVALID_REQUEST")
                .message("Invalid request")
                .extraError(Map.of("sort", String.format("Invalid field: %s", ex.getPropertyName())))
                .build();

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // Api path not found
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFoundException(NoResourceFoundException e) {
        ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .code("404000")
                .type("RESOURCE_NOT_FOUND")
                .message("Resource not found")
                .build();

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        e.printStackTrace();

        ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .code("500000")
                .type("INTERNAL_SERVER_ERROR")
                .message("Internal server error")
                .build();

        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
