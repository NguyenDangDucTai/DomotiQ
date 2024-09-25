package com.example.devicesservice.dtos.user_room;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DeleteRoomRequest {

    @NotBlank(message = "id must be not null or empty")
    private String id;

    public DeleteRoomRequest(String id) {
        this.id = id;
    }
}
