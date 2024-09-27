package com.example.devicesservice.dtos.modules.presence_sensor;

import lombok.Data;

import java.util.List;

@Data
public class SetTriggerRequest {

    private String moduleId;
    private List<PresenceSensorTriggerRequest> triggerOnDetectPresence;
    private List<PresenceSensorTriggerRequest> triggerOnDetectAbsence;

}
