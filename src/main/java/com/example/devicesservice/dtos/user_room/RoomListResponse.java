package com.example.devicesservice.dtos.user_room;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class RoomListResponse {

    private List<RoomMinResponse> rooms;

}
