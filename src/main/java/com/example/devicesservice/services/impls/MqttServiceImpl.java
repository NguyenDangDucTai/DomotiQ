package com.example.devicesservice.services.impls;

import com.example.devicesservice.models.MqttMessage;
import com.example.devicesservice.services.MqttService;
import com.example.devicesservice.utils.JsonConverter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Map;
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
    private final JsonConverter jsonConverter;
    private final ObjectMapper objectMapper;

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
        String payload = jsonConverter.toJson(message.getPayload());
        client.publishWith()
                .topic(message.getTopic())
                .payload(UTF_8.encode(payload))
                .send();
    }

    @Override
    public void subscribe(String topic) {
        client.subscribeWith()
                .topicFilter(topic)
                .send();
    }

    @Override
    public void subscribe(String topic, Consumer<MqttMessage> callback) {
        subscribe(topic);
        onReceiveMessage(topic, callback);
    }

    @Override
    public void onReceiveMessage(Consumer<MqttMessage> callback) {
        client.toAsync().publishes(ALL, publish -> {
            String topic = publish.getTopic().toString();
            String message = new String(publish.getPayloadAsBytes(), UTF_8);
            Map<String, Object> payload = jsonConverter.fromJson(message, Map.class);
            callback.accept(new MqttMessage(topic, payload, ZonedDateTime.now()));
        });
    }

    @Override
    public void onReceiveMessage(String topic, Consumer<MqttMessage> callback) {
        client.toAsync().publishes(ALL, publish -> {
            String receivedTopic = publish.getTopic().toString();
            if (receivedTopic.equals(topic)) {
                try {
                    String message = new String(publish.getPayloadAsBytes(), UTF_8);
                    Map<String, Object> payload = objectMapper.readValue(message, new TypeReference<>() {});
                    callback.accept(new MqttMessage(receivedTopic, payload, ZonedDateTime.now()));
                } catch (Exception e) {
                    System.out.println("Co loi trong qua trinh nhan message");
                    e.printStackTrace();
                }
            }
        });
    }

}
