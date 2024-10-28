package com.example.devicesservice.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Document(collection = "modules")
public class WeatherSensorModule extends Module {

    public WeatherSensorModule() {
        this.type = ModuleType.WEATHER_SENSOR;
    }

    public WeatherSensorModule(ObjectId id) {
        super(id);
        this.type = ModuleType.WEATHER_SENSOR;
    }
}
