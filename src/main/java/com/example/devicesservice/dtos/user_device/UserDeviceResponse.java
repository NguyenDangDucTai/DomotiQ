package com.example.devicesservice.dtos.user_device;

import com.example.devicesservice.dtos.user_module.UserModuleResponse;
import lombok.Data;

import java.util.List;

@Data
public class UserDeviceResponse {

    private String id;
    private List<UserModuleResponse> modules;

}
