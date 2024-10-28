package com.example.devicesservice.dtos.module_log;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class WeatherSensorDataLogResponse {

    private String id;
    private String timestamp;
    private double tem;
    private double hum;

}
