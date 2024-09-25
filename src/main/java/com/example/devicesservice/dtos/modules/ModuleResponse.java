package com.example.devicesservice.dtos.modules;

import com.example.devicesservice.dtos.command.CommandResponse;
import lombok.Data;

import java.util.Map;

@Data
public class ModuleResponse {

    private String id;
    private String name;
    private Map<String, CommandResponse> commands;

}
