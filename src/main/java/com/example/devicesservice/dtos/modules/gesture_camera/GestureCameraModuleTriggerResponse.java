package com.example.devicesservice.dtos.modules.gesture_camera;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GestureCameraModuleTriggerResponse {
    private String type;

    private String moduleId;
    private String deviceId;

    private String command;

    private String phone;

}
