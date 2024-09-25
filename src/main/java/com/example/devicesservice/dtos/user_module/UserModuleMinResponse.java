package com.example.devicesservice.dtos.user_module;

import lombok.Data;

@Data
public class UserModuleMinResponse {

    private String deviceId;
    private String moduleId;
    private String displayName;
    private String status;

}
