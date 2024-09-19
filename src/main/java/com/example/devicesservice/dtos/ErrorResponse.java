package com.example.devicesservice.dtos;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {

    private Integer status;
    private String code;
    private String type;
    private String message;
    private Map<String, Object> extraError;

}
