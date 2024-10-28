package com.example.devicesservice.configs;

import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5SimpleAuth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.nio.charset.StandardCharsets.UTF_8;

@Configuration
public class MqttConfig {

    @Value("${mqtt.server.host}")
    private String MQTT_HOST;

    @Value("${mqtt.server.port}")
    private int MQTT_PORT = 8883;

    @Value("${mqtt.auth.username}")
    private String MQTT_USERNAME;

    @Value("${mqtt.auth.password}")
    private String MQTT_PASSWORD;

    @Bean
    public Mqtt5BlockingClient mqttClient() {
        Mqtt5SimpleAuth auth = Mqtt5SimpleAuth.builder()
                .username(MQTT_USERNAME)
                .password(MQTT_PASSWORD.getBytes())
                .build();

        Mqtt5BlockingClient client = MqttClient.builder()
                .useMqttVersion5()
                .serverHost(MQTT_HOST)
                .serverPort(MQTT_PORT)
                .simpleAuth(auth)
                .sslWithDefaultConfig()
                .buildBlocking();

        client.connectWith()
                .simpleAuth()
                .username(MQTT_USERNAME)
                .password(UTF_8.encode(MQTT_PASSWORD))
                .applySimpleAuth()
                .send();

        return client;
    }

}
