package com.example.devicesservice.services;

import com.example.devicesservice.dtos.module_log.GetLogListRequest;
import com.example.devicesservice.dtos.module_log.WeatherSensorDataLogListResponse;
import org.springframework.stereotype.Service;

@Service
public interface WeatherSensorModuleService {

    WeatherSensorDataLogListResponse getLogs(GetLogListRequest request);

}
