package com.example.devicesservice.models;

import lombok.*;
import org.bson.types.ObjectId;

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

    private String name;

    private Map<String, Object> params;

    public MqttMessage toMqttMessage() {
        Map<String, Object> payload = new HashMap<>();
//        payload.put("module", module.getId().toHexString());

        return MqttMessage.builder()
                .topic("")
                .payload(payload)
                .build();
    }

}
