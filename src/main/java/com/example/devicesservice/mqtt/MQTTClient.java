package com.example.devicesservice.mqtt;

import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.exceptions.MqttDecodeException;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import static java.nio.charset.StandardCharsets.UTF_8;
import static com.hivemq.client.mqtt.MqttGlobalPublishFilter.ALL;
public class MQTTClient {

    private static final String host = "501a11ddd806403190adca1663a504eb.s1.eu.hivemq.cloud";
    private static final String username = "matyga2020";
    private static final String password = "01237333861Tai";

    private static final Mqtt5BlockingClient client = MqttClient.builder()
            .useMqttVersion5()
            .serverHost(host)
            .serverPort(8883)
            .sslWithDefaultConfig()
            .buildBlocking();


    public static void connect(){
        try{
            client.connectWith()
                    .simpleAuth()
                    .username(username)
                    .password(UTF_8.encode(password))
                    .applySimpleAuth()
                    .send();

            System.out.println("Connected hivemq successfully");

        } catch (MqttDecodeException e) {
            e.printStackTrace();
        }
    }

    public static void subscribeTopic(){
        try{
            client.subscribeWith()
                    .topicFilter("ab")
                    .send();
        } catch (MqttDecodeException e) {
            e.printStackTrace();
        }
    }

    public static void callback(){
        client.toAsync().publishes(ALL, publish -> {
            System.out.println("Received message: " +
                    publish.getTopic() + " -> " +
                    UTF_8.decode(publish.getPayload().get()));
        });
    }

    public static void pushTopic(String topic, String message){
        try{
            client.publishWith()
                    .topic(topic)
                    .payload(UTF_8.encode(message))
                    .send();
            System.out.println("Successfuly to push topic: " + message);
        }
        catch (MqttDecodeException e) {
            e.printStackTrace();
        }
    }


}
