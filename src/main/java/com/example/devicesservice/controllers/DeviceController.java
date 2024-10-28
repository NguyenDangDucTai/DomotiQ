package com.example.devicesservice.controllers;

import com.example.devicesservice.dtos.device.*;
import com.example.devicesservice.services.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;

    @GetMapping
    public ResponseEntity<DeviceListResponse> getDeviceList(@ModelAttribute GetDeviceListRequest request) {
        return ResponseEntity.ok(deviceService.getDeviceList(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceResponse> getDeviceById(@PathVariable String id) {
        return ResponseEntity.ok(deviceService.getDeviceById(new GetDeviceByIdRequest(id)));
    }

    @PostMapping
    public ResponseEntity<DeviceResponse> createDevice(@RequestBody CreateDeviceRequest request) {
        return ResponseEntity.ok(deviceService.createDevice(request));
    }

    @PutMapping
    public ResponseEntity<DeviceResponse> updateDevice(@RequestBody UpdateDeviceRequest request) {
        return ResponseEntity.ok(deviceService.updateDevice(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDevice(@PathVariable String id) {
        return ResponseEntity.ok(deviceService.deleteDevice(new DeleteDeviceRequest(id)));
    }

}
