package com.example.devicesservice.services;

import com.example.devicesservice.dtos.device.*;
import com.example.devicesservice.dtos.device.DeviceResponse;
import com.example.devicesservice.models.Device;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public interface DeviceService {

    DeviceListResponse getDeviceList(@Valid GetDeviceListRequest request);
    DeviceResponse getDeviceById(@Valid GetDeviceByIdRequest request);
    DeviceResponse createDevice(@Valid CreateDeviceRequest request);
    DeviceResponse updateDevice(@Valid UpdateDeviceRequest request);
    String deleteDevice(@Valid DeleteDeviceRequest request);

    Device findDeviceById(String id);

}
