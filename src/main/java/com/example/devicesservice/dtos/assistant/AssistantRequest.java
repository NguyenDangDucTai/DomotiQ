package com.example.devicesservice.dtos.assistant;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AssistantRequest {

    @NotBlank(message = "Command cannot be empty")
    private String command;

}
