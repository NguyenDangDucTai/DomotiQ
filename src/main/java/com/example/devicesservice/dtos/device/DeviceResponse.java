package com.example.devicesservice.dtos.device;

import com.example.devicesservice.dtos.modules.ModuleResponse;
import lombok.Data;

import java.util.List;

@Data
public class DeviceResponse {

    private String id;
    private String type;
    private String name;

    private List<ModuleResponse> modules;

}
