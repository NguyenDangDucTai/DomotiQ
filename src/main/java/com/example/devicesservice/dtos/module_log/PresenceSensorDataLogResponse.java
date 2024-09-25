package com.example.devicesservice.dtos.module_log;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class PresenceSensorDataLogResponse {

    private String id;
    private String timestamp;
    private Integer state;

}
