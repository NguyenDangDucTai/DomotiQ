package com.example.devicesservice.dtos.modules;

import com.example.devicesservice.dtos.command.CommandResponse;
import com.example.devicesservice.models.ModuleType;
import lombok.Data;

import java.util.Map;

@Data
public class ModuleResponse {

    private String id;
    private String name;
    private ModuleType type;
    private Map<String, CommandResponse> commands;

}
