package com.example.devicesservice.dtos.user_room;

import com.example.devicesservice.models.UserRoomStatus;
import com.example.devicesservice.utils.validators.EnumValidator;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Set;

@Data
public class CreateRoomRequest {

    @NotBlank(message = "name must be not null or empty")
    private String name;

    @EnumValidator(enumClass = UserRoomStatus.class, message = "status must be one of [VISIBLE, HIDDEN]")
    private String status;

    private Set<String> moduleIds;

}
