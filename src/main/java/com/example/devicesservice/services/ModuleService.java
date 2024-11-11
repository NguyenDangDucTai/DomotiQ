package com.example.devicesservice.services;

import com.example.devicesservice.dtos.modules.ModulePresenceByIdResponse;
import com.example.devicesservice.dtos.modules.ModuleResponse;
import com.example.devicesservice.models.Module;
import com.example.devicesservice.models.ModuleType;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public interface ModuleService {

    Module findModuleById(String id);
    Module findModuleById(String id, ModuleType type);

    ModulePresenceByIdResponse findModulePresenceById(String id);
}
