package com.example.deviceservice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DevicesOwend {
    private String deviceId;
    private String displayName;
    private String roomName;
    private List<ModuleUsed> modules;
}
