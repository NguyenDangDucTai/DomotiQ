package com.example.devicesservice.dtos.auth;

import com.example.devicesservice.dtos.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AuthResponse {

    private String accessToken;
    private UserResponse user;

}
