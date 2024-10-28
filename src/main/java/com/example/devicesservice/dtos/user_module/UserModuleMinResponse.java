package com.example.devicesservice.dtos.user_module;

import com.example.devicesservice.models.ModuleType;
import lombok.Data;

@Data
public class UserModuleMinResponse {

    private String deviceId;
    private String moduleId;
    private String displayName;
    private ModuleType type;
    private String status;

}
