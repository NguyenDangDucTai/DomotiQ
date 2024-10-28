package com.example.devicesservice.dtos.user_device;

import com.example.devicesservice.dtos.device.DeviceMinResponse;
import lombok.Data;

@Data
public class UserDeviceMinResponse {

    private String id;
    private DeviceMinResponse device;

}
