package com.example.devicesservice.models;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Command {

    @EqualsAndHashCode.Include
    private ObjectId id;

    @DBRef(lazy = true)
    private Device device;

    private String name;

    private Map<String, Object> params;

    public MqttMessage toMqttMessage() {
        Map<String, Object> payload = new HashMap<>(params);
        payload.put("cmd", name);

        return MqttMessage.builder()
                .topic(device.getId().toHexString())
                .payload(payload)
                .build();
    }

}
