package com.example.devicesservice.controllers;

import com.example.devicesservice.dtos.module_log.GetLogListRequest;
import com.example.devicesservice.services.WeatherSensorModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/modules/weather-sensors")
@RequiredArgsConstructor
public class WeatherSensorModuleController {

    private final WeatherSensorModuleService weatherSensorModuleService;

    @GetMapping("/logs")
    public ResponseEntity<Object> getLogs(@RequestBody GetLogListRequest request) {
        return ResponseEntity.ok(weatherSensorModuleService.getLogs(request));
    }

}
