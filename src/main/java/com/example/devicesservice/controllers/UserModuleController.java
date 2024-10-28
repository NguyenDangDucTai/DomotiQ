package com.example.devicesservice.controllers;

import com.example.devicesservice.dtos.user_module.*;
import com.example.devicesservice.services.UserModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/modules")
@RequiredArgsConstructor
public class UserModuleController {

    private final UserModuleService userModuleService;

    @GetMapping
    public ResponseEntity<UserModuleListResponse> getModuleListByUser() {
        return ResponseEntity.ok(userModuleService.getModuleListByUser());
    }

    @GetMapping("/{moduleId}")
    public ResponseEntity<UserModuleResponse> getModuleFromUserById(@PathVariable String moduleId) {
        return ResponseEntity.ok(userModuleService.getModuleFromUserById(new GetModuleFromUserByIdRequest(moduleId)));
    }

    @PutMapping
    public ResponseEntity<UserModuleResponse> updateUserModule(@RequestBody UpdateModuleFromUserRequest request) {
        return ResponseEntity.ok(userModuleService.updateModuleFromUser(request));
    }

}
