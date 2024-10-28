package com.example.devicesservice.dtos.device;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class DeviceListResponse {

    private List<DeviceMinResponse> items;

}
