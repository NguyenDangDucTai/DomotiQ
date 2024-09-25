package com.example.devicesservice.dtos.device;

import com.example.devicesservice.dtos.modules.ModuleRequest;
import com.example.devicesservice.models.DeviceType;
import com.example.devicesservice.utils.validators.EnumValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class CreateDeviceRequest {

    @EnumValidator(enumClass = DeviceType.class, message = "type invalid", required = true)
    private String type;

    @NotBlank(message = "name must be not null or empty")
    private String name;

    @NotEmpty(message = "modules must be not empty")
    private List<ModuleRequest> modules;

}
