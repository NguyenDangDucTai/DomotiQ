package com.example.devicesservice.dtos.device;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeleteDeviceRequest {

    @NotBlank(message = "id must be not null or empty")
    private String id;

}
