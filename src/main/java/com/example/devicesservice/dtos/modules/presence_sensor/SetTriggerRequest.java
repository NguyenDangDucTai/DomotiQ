package com.example.devicesservice.dtos.modules.presence_sensor;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class SetTriggerRequest {

    @NotBlank(message = "moduleId is must be not null or empty")
    private String moduleId;

    private List<PresenceSensorTriggerRequest> triggerOnDetectPresence;
    private List<PresenceSensorTriggerRequest> triggerOnDetectAbsence;

}
