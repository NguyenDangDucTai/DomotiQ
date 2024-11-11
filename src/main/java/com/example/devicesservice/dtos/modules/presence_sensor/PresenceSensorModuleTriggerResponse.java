package com.example.devicesservice.dtos.modules.presence_sensor;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PresenceSensorModuleTriggerResponse {
    private String type;

    private String moduleId;

    private String command;

    private String phone;

}
