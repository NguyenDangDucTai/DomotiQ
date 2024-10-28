package com.example.devicesservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PresenceSensorModuleTrigger {

    private Type type;

    @DBRef(lazy = true)
    private Module module;
    private String command;

    private String phone;

    public enum Type {
        DEVICE_CONTROL,
        NOTIFICATION
    }

}
