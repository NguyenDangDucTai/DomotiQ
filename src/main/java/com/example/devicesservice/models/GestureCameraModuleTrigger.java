package com.example.devicesservice.models;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class GestureCameraModuleTrigger {

    private GestureType type;

    @DBRef(lazy = true)
    private Module module;
    private String command;
    private Device device;




}
