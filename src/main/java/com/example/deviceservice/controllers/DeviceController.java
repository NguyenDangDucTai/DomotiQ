package com.example.deviceservice.controllers;


import com.example.devicesservice.dtos.AddNewDevice;
import com.example.devicesservice.services.DeviceServices;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/devices")
@AllArgsConstructor
public class DeviceController {

    private final DeviceServices deviceServices;

    @GetMapping("/{deviceId}")
    public ResponseEntity<?> getDeviceById(@PathVariable("deviceId") ObjectId id){
        return ResponseEntity.ok(deviceServices.getById(id));
    }

    @PostMapping("/addNewDevice")
    public void addNewDevice(@RequestBody AddNewDevice request){
        deviceServices.addDevice(request);
    }






}
