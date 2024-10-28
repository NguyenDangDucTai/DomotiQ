package com.example.devicesservice.controllers;

import com.example.devicesservice.dtos.user_room.*;
import com.example.devicesservice.services.UserRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/rooms")
@RequiredArgsConstructor
public class UserRoomController {

    private final UserRoomService userRoomService;

    @GetMapping
    public ResponseEntity<RoomListResponse> getRoomListByUser(@ModelAttribute GetRoomListByUserRequest request) {
        return ResponseEntity.ok(userRoomService.getRoomListByUser(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> getRoomById(@PathVariable String id) {
        return ResponseEntity.ok(userRoomService.getRoomById(new GetRoomByIdRequest(id)));
    }

    @PostMapping
    public ResponseEntity<RoomResponse> createRoom(@RequestBody CreateRoomRequest request) {
        return ResponseEntity.ok(userRoomService.createRoom(request));
    }

    @PutMapping
    public ResponseEntity<RoomResponse> updateRoom(@RequestBody UpdateRoomRequest request) {
        return ResponseEntity.ok(userRoomService.updateRoom(request));
    }

    @DeleteMapping("/{id}")
    public String deleteRoom(@PathVariable String id) {
        return userRoomService.deleteRoom(new DeleteRoomRequest(id));
    }

}