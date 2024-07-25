package com.example.deviceservice.dtos;


import lombok.Data;

@Data
public class RenameDevice {
    private String deviceId;
    private String displayName;

}
