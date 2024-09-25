package com.example.devicesservice.controllers;


import com.example.devicesservice.dtos.user_device.*;
import com.example.devicesservice.services.UserDeviceService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/devices")
@AllArgsConstructor
public class UserDeviceController {

    private final UserDeviceService userDeviceService;

    @GetMapping
    public ResponseEntity<UserDeviceListResponse> getDeviceListByUser(@ModelAttribute GetDeviceListByUserRequest request){
        return ResponseEntity.ok(userDeviceService.getDeviceListByUser(request));
    }

    @GetMapping("/{deviceId}")
    public ResponseEntity<UserDeviceResponse> getDeviceFromUserById(@PathVariable String deviceId) {
        return ResponseEntity.ok(userDeviceService.getDeviceFromUserById(new GetDeviceFromUserByIdRequest(deviceId)));
    }

    @PostMapping("/add")
    public ResponseEntity<UserDeviceResponse> addDeviceToUser(@RequestBody AddDeviceToUserRequest request){
        return ResponseEntity.ok(userDeviceService.addDeviceToUser(request));
    }

    @DeleteMapping("/{deviceId}")
    public String removeDeviceFromUser(@PathVariable String deviceId) {
        return userDeviceService.removeDeviceFromUser(new RemoveDeviceFromUserRequest(deviceId));
    }

}
