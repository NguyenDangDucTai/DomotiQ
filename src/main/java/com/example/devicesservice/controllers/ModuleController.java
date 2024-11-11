package com.example.devicesservice.controllers;


import com.example.devicesservice.dtos.modules.ModulePresenceByIdResponse;
import com.example.devicesservice.dtos.modules.ModuleResponse;
import com.example.devicesservice.models.Module;
import com.example.devicesservice.models.ModuleType;
import com.example.devicesservice.services.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/modules")
@RequiredArgsConstructor
public class ModuleController {

    private final ModuleService moduleService;

    @GetMapping("/{moduleId}")
    public ResponseEntity<ModulePresenceByIdResponse> getModulePresenceById(@PathVariable String moduleId){
        return ResponseEntity.ok(moduleService.findModulePresenceById(moduleId));
    }


}
