package com.example.deviceservice.controllers;


import com.example.deviceservice.dtos.DeleteDeviceFromUser;
import com.example.deviceservice.dtos.FindDeviceByRoomName;
import com.example.deviceservice.dtos.RenameDevice;
import com.example.deviceservice.models.DevicesOwend;
import com.example.deviceservice.services.UserServices;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServices userServices;

    @GetMapping
    public ResponseEntity<?> getAllUser(){
        return ResponseEntity.ok(userServices.getAll());
    }


    @GetMapping("/{userId}/findById")
    public ResponseEntity<?> findById(@PathVariable("userId") ObjectId id){
        return ResponseEntity.ok(userServices.findById(id));
    }

    @PostMapping("/{userId}/addDevices")
    public void addDeviceToUser(@PathVariable ObjectId userId,@RequestBody DevicesOwend request) {
        userServices.addDeviceToUser(userId ,request);
    }

    @PostMapping("/{userId}/removeDevice")
    public void removeDeviceFromUser(@PathVariable("userId") ObjectId userId, @RequestBody DeleteDeviceFromUser request){
        userServices.deleteDevicefromUser(userId, request);
    }

    @GetMapping("/{userId}/findDeviceByRoomName")
    public ResponseEntity<?> findDeviceByRoomName(@PathVariable("userId") ObjectId userId,@RequestBody FindDeviceByRoomName request) {
        return ResponseEntity.ok(userServices.findDeviceByRoomName(userId, request));
    }

    @PostMapping("/{userId}/renameDevice")
    public void renameDevices(@PathVariable("userId") ObjectId userId, @RequestBody RenameDevice request){
        userServices.renameDevice(userId, request);
    }


}
