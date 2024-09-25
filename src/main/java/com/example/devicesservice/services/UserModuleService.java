package com.example.devicesservice.services;

import com.example.devicesservice.dtos.user_module.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public interface UserModuleService {

    UserModuleListResponse getModuleListByUser(@Valid GetModuleListByUserRequest request);
    UserModuleResponse getModuleFromUserById(@Valid GetModuleFromUserByIdRequest request);
    UserModuleResponse updateModuleFromUser(@Valid UpdateModuleFromUserRequest request);

}
