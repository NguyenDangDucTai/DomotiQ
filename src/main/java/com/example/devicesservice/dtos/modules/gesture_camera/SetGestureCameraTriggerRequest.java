package com.example.devicesservice.dtos.modules.gesture_camera;

import com.example.devicesservice.dtos.modules.presence_sensor.PresenceSensorTriggerRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class SetGestureCameraTriggerRequest {

    @NotBlank(message = "moduleId is must be not null or empty")
    private String moduleId;

    private List<GestureCameraModuleTriggerRequest> gestureCameraModuleTriggers;
}
