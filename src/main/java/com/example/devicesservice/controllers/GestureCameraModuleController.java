package com.example.devicesservice.controllers;

import com.example.devicesservice.dtos.modules.gesture_camera.SetGestureCameraTriggerRequest;
import com.example.devicesservice.dtos.modules.presence_sensor.SetTriggerRequest;
import com.example.devicesservice.models.Module;
import com.example.devicesservice.services.GestureCameraModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/modules/gesture-camera")
@RequiredArgsConstructor
public class GestureCameraModuleController {

    private final GestureCameraModuleService gestureCameraModuleService;

    @PostMapping("/triggers")
    public ResponseEntity<Module> setTrigger(@RequestBody SetGestureCameraTriggerRequest request) {
        return ResponseEntity.ok(gestureCameraModuleService.setTrigger(request));
    }


}
