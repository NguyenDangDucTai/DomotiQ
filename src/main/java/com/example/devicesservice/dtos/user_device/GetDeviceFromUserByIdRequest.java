package com.example.devicesservice.dtos.user_device;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetDeviceFromUserByIdRequest {

    @NotBlank(message = "deviceId must be not null or empty")
    private String deviceId;

}
