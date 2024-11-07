package com.example.devicesservice.dtos.user_module;

import com.example.devicesservice.models.ModuleType;
import com.example.devicesservice.models.UserModuleStatus;
import com.example.devicesservice.utils.validators.EnumValidator;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AddModuleToUserRequest {

    @NotBlank(message = "moduleId must be not null or empty")
    @Length(min = 24, max = 24, message = "moduleId must be 24 characters long")
    @EqualsAndHashCode.Include
    private String moduleId;

    @NotBlank(message = "displayName must be not null or empty")
    private String displayName;

    @EnumValidator(enumClass = UserModuleStatus.class, message = "status must be one of these values: [VISIBLE, HIDDEN]", required = true)
    private String status;

    @NotBlank(message = "roomId must be not null or empty")
    @Length(min = 24, max = 24, message = "roomId must be 24 characters long")
    @EqualsAndHashCode.Include
    private String roomId;


    @EnumValidator(enumClass = ModuleType.class, message = "status must be one of these values: [SOCKET, WEATHER_SENSOR, PRESENCE_SENSOR]", required = true)
    private String type;



}
