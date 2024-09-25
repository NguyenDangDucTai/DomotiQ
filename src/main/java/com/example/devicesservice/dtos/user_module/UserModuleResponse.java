package com.example.devicesservice.dtos.user_module;

import com.example.devicesservice.dtos.user_room.RoomMinResponse;
import lombok.Data;

@Data
public class UserModuleResponse {

    private String deviceId;
    private String moduleId;
    private String displayName;
    private String status;
    private RoomMinResponse room;

}
