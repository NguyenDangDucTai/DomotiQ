package com.example.devicesservice.services.impls;

import com.example.devicesservice.models.MqttMessage;
import com.example.devicesservice.services.MqttService;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

import static com.hivemq.client.mqtt.MqttGlobalPublishFilter.ALL;
import static java.nio.charset.StandardCharsets.UTF_8;

@Service
@RequiredArgsConstructor
public class MqttServiceImpl implements MqttService {

    @Value("${mqtt.auth.username}")
    private String MQTT_USERNAME;

    @Value("${mqtt.auth.password}")
    private String MQTT_PASSWORD;

    private final Mqtt5BlockingClient client;

    @Override
    public void connect() {
        client.connectWith()
                .simpleAuth()
                .username(MQTT_USERNAME)
                .password(UTF_8.encode(MQTT_PASSWORD))
                .applySimpleAuth()
                .send();
    }

    @Override
    public void disconnect() {
        client.disconnect();
    }

    @Override
    public void publish(MqttMessage message) {
        client.publishWith()
                .topic(message.getTopic())
                .payload(UTF_8.encode(message.getMessage()))
                .send();
    }

    @Override
    public void subscribe(String topic) {
        client.subscribeWith()
                .topicFilter(topic)
                .send();
    }

    @Override
    public void onReceiveMessage(Consumer<MqttMessage> callback) {
        client.toAsync().publishes(ALL, publish -> {
            String topic = publish.getTopic().toString();
            String message = new String(publish.getPayloadAsBytes(), UTF_8);
            callback.accept(new MqttMessage(topic, message));
        });
    }

}
