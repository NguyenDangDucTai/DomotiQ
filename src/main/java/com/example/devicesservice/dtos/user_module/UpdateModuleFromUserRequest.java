package com.example.devicesservice.dtos.user_module;

import com.example.devicesservice.models.UserModuleStatus;
import com.example.devicesservice.utils.validators.EnumValidator;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UpdateModuleFromUserRequest {

    @NotBlank(message = "moduleId must be not null or empty")
    @Length(min = 24, max = 24, message = "moduleId must be a string with a length of 24")
    private String moduleId;

    @NotBlank(message = "displayName must be not null or empty")
    private String displayName;

    @EnumValidator(enumClass = UserModuleStatus.class, message = "status must be one of these values: [VISIBLE, HIDDEN]", required = true)
    private String status;

    private String roomId;

}
