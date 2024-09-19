package com.example.devicesservice.services;

import com.example.devicesservice.contexts.AuthCertificate;
import com.example.devicesservice.dtos.auth.AuthResponse;
import com.example.devicesservice.dtos.auth.LoginRequest;
import com.example.devicesservice.dtos.auth.SignupRequest;
import com.example.devicesservice.dtos.user.UserResponse;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public interface AuthService {

    UserResponse signup(@Valid SignupRequest request);
    AuthResponse login(@Valid LoginRequest request);

    AuthCertificate getAuthCertificateByUserId(String userId);

}
