package com.example.devicesservice.dtos.modules;

import com.example.devicesservice.dtos.command.CommandResponse;
import com.example.devicesservice.dtos.modules.presence_sensor.PresenceSensorModuleTriggerResponse;
import com.example.devicesservice.models.GestureCameraModuleTrigger;
import com.example.devicesservice.models.ModuleType;
import com.example.devicesservice.models.PresenceSensorModuleTrigger;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
public class ModuleResponse {

    private String id;
    private String name;
    private ModuleType type;
    private Map<String, CommandResponse> commands;

    private List<PresenceSensorModuleTrigger> triggerOnDetectPresence;
    private List<PresenceSensorModuleTrigger> triggerOnDetectAbsence;
    private List<GestureCameraModuleTrigger> gestureCameraModuleTriggers;

}
