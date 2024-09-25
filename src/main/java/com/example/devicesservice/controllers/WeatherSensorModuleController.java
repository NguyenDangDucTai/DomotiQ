package com.example.devicesservice.controllers;

import com.example.devicesservice.dtos.module_log.GetLogListRequest;
import com.example.devicesservice.services.WeatherSensorModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
