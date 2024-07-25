package com.example.devicesservice.dtos;


import lombok.Data;

@Data
public class RenameDevice {
    private String deviceId;
    private String displayName;

}
