package com.example.devicesservice.factories;

import com.example.devicesservice.models.*;
import com.example.devicesservice.models.Module;

public class ModuleFactory {

    public static Module createModule(ModuleType type) {
        return switch (type) {
            case SOCKET -> createSocketModule();
            case WEATHER_SENSOR -> createWeatherSensorModule();
            case PRESENCE_SENSOR -> createPresenceSensorModule();
            case GESTURE_CAMERA -> createGesturCameraModule();
        };
    }


    private static Module createPresenceSensorModule() {
        return new PresenceSensorModule();
    }

    private static Module createWeatherSensorModule() {
        return new WeatherSensorModule();
    }

    private static Module createSocketModule() {
        return new SocketModule();
    }

    private static Module createGesturCameraModule() {return new GestureCameraModule();}

}
