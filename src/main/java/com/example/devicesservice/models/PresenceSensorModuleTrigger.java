package com.example.devicesservice.models;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
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
