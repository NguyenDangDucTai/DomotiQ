package com.example.devicesservice.dtos.user_device;

import com.example.devicesservice.dtos.user_module.AddModuleToUserRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
public class AddDeviceToUserRequest {

    @NotBlank(message = "deviceId must be not null or empty")
    @Length(min = 24, max = 24, message = "deviceId must be 24 characters long")
    private String deviceId;

    @NotEmpty(message = "modules must be not null or empty")
    private List<AddModuleToUserRequest> modules;

}
