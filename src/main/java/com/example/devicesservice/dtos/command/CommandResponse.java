package com.example.devicesservice.dtos.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CommandResponse {

    private String id;
    private String name;

}
