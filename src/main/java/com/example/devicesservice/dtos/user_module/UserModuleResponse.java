package com.example.devicesservice.dtos.user_module;

import com.example.devicesservice.dtos.device.DeviceMinResponse;
import com.example.devicesservice.dtos.modules.ModuleResponse;
import com.example.devicesservice.dtos.user_room.RoomMinResponse;
import lombok.Data;

@Data
public class UserModuleResponse {

    private DeviceMinResponse device;
    private ModuleResponse module;
    private String displayName;
    private String status;
    private RoomMinResponse room;

}
