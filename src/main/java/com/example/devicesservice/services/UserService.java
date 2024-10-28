package com.example.devicesservice.services;

import com.example.devicesservice.dtos.user.UserResponse;

public interface UserService {

    UserResponse getUserById(String id);

}
