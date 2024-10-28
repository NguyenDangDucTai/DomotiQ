package com.example.devicesservice.services;

import com.example.devicesservice.dtos.assistant.AssistantRequest;
import com.example.devicesservice.dtos.assistant.AssistantResponse;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.io.IOException;

@Service
@Validated
public interface AssistantService {

    AssistantResponse process(@Valid AssistantRequest request) throws IOException;

}
