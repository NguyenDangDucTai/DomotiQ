package com.example.devicesservice.dtos.modules.presence_sensor;

import com.example.devicesservice.models.PresenceSensorModuleTrigger;
import com.example.devicesservice.utils.validators.EnumValidator;
import lombok.Data;

@Data
public class PresenceSensorTriggerRequest {

    @EnumValidator(enumClass = PresenceSensorModuleTrigger.Type.class, message = "Invalid trigger type", required = true)
    private String type;

    private String moduleId;
    private String command;

    private String phone;

}
