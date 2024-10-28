package com.example.devicesservice.dtos.user_module;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
public class GetModuleFromUserByIdRequest {

    @NotBlank(message = "moduleId must be not null or empty")
    @Length(min = 24, max = 24, message = "moduleId must be 24 characters")
    private String moduleId;

}
