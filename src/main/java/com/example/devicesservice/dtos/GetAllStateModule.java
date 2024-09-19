package com.example.devicesservice.dtos;

import lombok.Data;

import java.util.List;

@Data
public class GetAllStateModule {
    private String deviceId;
    private List<ModuleState> modules;
}
