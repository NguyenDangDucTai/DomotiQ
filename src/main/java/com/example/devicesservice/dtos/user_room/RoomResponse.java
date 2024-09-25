package com.example.devicesservice.dtos.user_room;

import com.example.devicesservice.dtos.user_module.UserModuleMinResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomResponse {

    private String id;
    private String name;
    private String status;

    private List<UserModuleMinResponse> modules;

}
