package com.example.devicesservice.dtos;

import com.example.devicesservice.models.enums.Comment;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SwitchLight {
    private String deviceId;
    private Comment cmd;
    private String module;
    private boolean state;

}
