package com.example.devicesservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MqttMessage {

    private String topic;
    private Map<String, Object> payload;
    private ZonedDateTime timestamp;

}
