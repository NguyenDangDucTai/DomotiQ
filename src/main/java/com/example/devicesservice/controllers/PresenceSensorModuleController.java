package com.example.devicesservice.controllers;

import com.example.devicesservice.dtos.module_log.GetLogListRequest;
import com.example.devicesservice.dtos.module_log.PresenceSensorDataLogListResponse;
import com.example.devicesservice.dtos.modules.presence_sensor.SetTriggerRequest;
import com.example.devicesservice.models.Module;
import com.example.devicesservice.services.PresenceSensorModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/modules/presence-sensors")
@RequiredArgsConstructor
public class PresenceSensorModuleController {

    private final PresenceSensorModuleService presenceSensorModuleService;

    @GetMapping("/logs")
    public ResponseEntity<PresenceSensorDataLogListResponse> getLogs(@RequestBody GetLogListRequest request) {
        return ResponseEntity.ok(presenceSensorModuleService.getLogs(request));
    }

    @PostMapping("/triggers")
    public ResponseEntity<Module> setTrigger(@RequestBody SetTriggerRequest request) {
        return ResponseEntity.ok(presenceSensorModuleService.setTrigger(request));
    }

}
