package com.example.devicesservice.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AddNewDevice{
    private String productId;
    private String name;
}
