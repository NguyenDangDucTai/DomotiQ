package com.example.devicesservice.dtos.modules;

import com.example.devicesservice.dtos.command.CommandRequest;
import com.example.devicesservice.models.ModuleType;
import com.example.devicesservice.utils.validators.EnumValidator;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
public class ModuleRequest {

    @Length(min = 24, max = 24, message = "id must be 24 characters")
    private String id;

    @EnumValidator(enumClass = ModuleType.class, message = "type is invalid", required = true)
    private String type;

    @NotBlank(message = "name must be not null or empty")
    private String name;

    private List<CommandRequest> commands;

}
