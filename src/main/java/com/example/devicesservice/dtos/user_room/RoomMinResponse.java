package com.example.devicesservice.dtos.user_room;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomMinResponse {

    private String id;
    private String name;
    private String status;

}
