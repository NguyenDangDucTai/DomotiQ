package com.example.devicesservice.services;

import com.example.devicesservice.dtos.user_room.*;
import com.example.devicesservice.models.UserRoom;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public interface UserRoomService {

    RoomListResponse getRoomListByUser(@Valid GetRoomListByUserRequest request);
    RoomResponse getRoomById(@Valid GetRoomByIdRequest request);
    RoomResponse createRoom(@Valid CreateRoomRequest request);
    RoomResponse updateRoom(@Valid UpdateRoomRequest request);
    String deleteRoom(@Valid DeleteRoomRequest request);

    UserRoom findRoomById(String id);

}
