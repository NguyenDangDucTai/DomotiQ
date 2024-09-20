package com.example.devicesservice.services;

import com.example.devicesservice.models.MqttMessage;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public interface MqttService {

    void connect();
    void disconnect();
    void publish(MqttMessage message);
    void subscribe(String topic);
    void onReceiveMessage(Consumer<MqttMessage> callback);

}
