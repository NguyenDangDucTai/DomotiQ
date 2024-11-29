package com.example.devicesservice.dtos.modules.gesture_camera;

import com.example.devicesservice.models.GestureType;
import com.example.devicesservice.utils.validators.EnumValidator;
import lombok.Data;

@Data
public class GestureCameraModuleTriggerRequest {
    @EnumValidator(enumClass = GestureType.class, message = "Invalid trigger type", required = true)
    private String type;

    private String moduleId;
    private String command;
}
