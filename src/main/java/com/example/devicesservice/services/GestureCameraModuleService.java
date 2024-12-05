package com.example.devicesservice.services;

import com.example.devicesservice.dtos.modules.gesture_camera.SetGestureCameraTriggerRequest;
import com.example.devicesservice.models.GestureCameraModule;

public interface GestureCameraModuleService {
    GestureCameraModule setTrigger(SetGestureCameraTriggerRequest request);
}
