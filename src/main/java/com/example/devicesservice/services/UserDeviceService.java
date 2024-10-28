package com.example.devicesservice.services;

import com.example.devicesservice.dtos.user_device.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public interface UserDeviceService {

    UserDeviceListResponse getDeviceListByUser(@Valid GetDeviceListByUserRequest request);
    UserDeviceResponse getDeviceFromUserById(@Valid GetDeviceFromUserByIdRequest request);
    UserDeviceResponse addDeviceToUser(@Valid AddDeviceToUserRequest request);
    UserDeviceResponse updateDeviceFromUser(@Valid UpdateDeviceFromUserRequest request);
    String removeDeviceFromUser(@Valid RemoveDeviceFromUserRequest request);

}
