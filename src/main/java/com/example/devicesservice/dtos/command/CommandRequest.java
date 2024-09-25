package com.example.devicesservice.dtos.command;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.Map;

@Data
public class CommandRequest {

    @Length(min = 24, max = 24, message = "id must be 24 characters")
    private String id;

    @NotBlank(message = "name must be not null or empty")
    private String name;

    private Map<String, Object> params;

}
