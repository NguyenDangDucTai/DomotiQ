package com.example.devicesservice.dtos.modules.presence_sensor;

import com.example.devicesservice.models.PresenceSensorModuleTrigger;
import lombok.Data;

@Data
public class SetTriggerRequest {

    private String moduleId;
    private PresenceSensorModuleTrigger triggerOnDetectPresence;
    private PresenceSensorModuleTrigger triggerOnDetectAbsence;

}
