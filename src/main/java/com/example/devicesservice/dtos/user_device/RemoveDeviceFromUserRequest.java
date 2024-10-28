package com.example.devicesservice.dtos.user_device;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
public class RemoveDeviceFromUserRequest {

    @NotBlank(message = "deviceId must be not null or empty")
    @Length(min = 24, max = 24, message = "deviceId must be 24 characters long")
    private String deviceId;

}
