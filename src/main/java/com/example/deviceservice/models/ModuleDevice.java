package com.example.deviceservice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ModuleDevice {
    private String idModule;
    private String type;
    private String timestamp;
    private String status;
}
