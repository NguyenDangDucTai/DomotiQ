package com.example.devicesservice.services;

import com.example.devicesservice.dtos.module_log.GetLogListRequest;
import com.example.devicesservice.dtos.module_log.SocketLogListResponse;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public interface SocketModuleService {

    SocketLogListResponse getLogs(@Valid GetLogListRequest request);

}
