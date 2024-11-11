package com.example.devicesservice.dtos.modules;

import com.example.devicesservice.dtos.modules.presence_sensor.PresenceSensorModuleTriggerResponse;
import com.example.devicesservice.models.ModuleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
public class ModulePresenceByIdResponse {
    private String id;
    private String name;
    private ModuleType type;
    private List<PresenceSensorModuleTriggerResponse> triggerOnDetectPresence;
    private List<PresenceSensorModuleTriggerResponse> triggerOnDetectAbsence;
}
