package com.example.devicesservice.mappers;

import com.example.devicesservice.dtos.module_log.WeatherSensorDataLogResponse;
import com.example.devicesservice.models.WeatherSensorDataLog;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WeatherSensorDataLogMapper {

    WeatherSensorDataLogResponse toWeatherSensorDataLogResponse(WeatherSensorDataLog log);

    default String mapId(ObjectId id) {
        if(id == null) return null;
        return id.toHexString();
    }

}
