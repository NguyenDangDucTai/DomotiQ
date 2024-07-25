package com.example.devicesservice.dtos;

import com.example.devicesservice.models.DevicesOwend;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class AddDeviceToUser {
    private String id;
    private String displayName;

    @JsonProperty("module")
    private DevicesOwend devicesOwends;
}
