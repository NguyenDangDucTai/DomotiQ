package com.example.devicesservice.dtos.device;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class GetDeviceByIdRequest {

    @NotBlank(message = "Id is must be not null or empty")
    @Length(min = 24, max = 24, message = "id must be 24 characters")
    private String id;

    public GetDeviceByIdRequest(String deviceId) {
        this.id = deviceId;
    }
}
