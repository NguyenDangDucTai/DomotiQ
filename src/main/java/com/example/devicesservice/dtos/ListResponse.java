package com.example.devicesservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@AllArgsConstructor
@SuperBuilder
public class ListResponse<T> {

    private Metadata metadata;
    private List<T> items;

}
