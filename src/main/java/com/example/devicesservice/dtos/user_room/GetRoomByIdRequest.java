package com.example.devicesservice.dtos.user_room;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetRoomByIdRequest {

    @NotBlank(message = "id must be not null or empty")
    private String id;

}
