package com.example.devicesservice.controllers;

import com.example.devicesservice.dtos.module_log.GetLogListRequest;
import com.example.devicesservice.dtos.module_log.SocketLogListResponse;
import com.example.devicesservice.services.SocketModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/modules/sockets")
@RequiredArgsConstructor
public class SocketModuleController {

    private final SocketModuleService socketModuleService;

    @GetMapping("/logs")
    public ResponseEntity<SocketLogListResponse> getLogs(@RequestBody GetLogListRequest request) {
        return ResponseEntity.ok(socketModuleService.getLogs(request));
    }

}
