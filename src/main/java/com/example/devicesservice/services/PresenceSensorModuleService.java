package com.example.devicesservice.services;

import com.example.devicesservice.dtos.module_log.GetLogListRequest;
import com.example.devicesservice.dtos.module_log.PresenceSensorDataLogListResponse;
import com.example.devicesservice.dtos.modules.presence_sensor.SetTriggerRequest;
import com.example.devicesservice.models.PresenceSensorDataLog;
import com.example.devicesservice.models.PresenceSensorModule;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PresenceSensorModuleService {

    PresenceSensorModule setTrigger(SetTriggerRequest request);
    PresenceSensorDataLogListResponse getLogs(GetLogListRequest request);

}
