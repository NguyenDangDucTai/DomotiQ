package com.example.devicesservice.controllers;

import com.example.devicesservice.dtos.assistant.AssistantRequest;
import com.example.devicesservice.dtos.assistant.AssistantResponse;
import com.example.devicesservice.services.AssistantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/assistant")
@RequiredArgsConstructor
public class AssistantController {

    private final AssistantService assistantService;

    @PostMapping
    public ResponseEntity<AssistantResponse> processQuery(@RequestBody AssistantRequest request) throws IOException {
        return ResponseEntity.ok(assistantService.process(request));
    }

}
