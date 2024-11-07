package com.example.devicesservice.controllers;


import com.example.devicesservice.dtos.modules.ModuleResponse;
import com.example.devicesservice.models.Module;
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
    public ResponseEntity<Module> getModuleById(@PathVariable String moduleId){
        System.out.println("555555555555" + moduleId);
        Module module = moduleService.findModuleById(moduleId);
        System.out.println("4444444: " + module);
        return ResponseEntity.ok(module);
    }


}
