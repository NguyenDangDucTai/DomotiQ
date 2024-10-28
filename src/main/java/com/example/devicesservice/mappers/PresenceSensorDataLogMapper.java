package com.example.devicesservice.mappers;

import com.example.devicesservice.dtos.module_log.PresenceSensorDataLogResponse;
import com.example.devicesservice.models.PresenceSensorDataLog;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PresenceSensorDataLogMapper {

    PresenceSensorDataLogResponse toPresenceSensorDataLogResponse(PresenceSensorDataLog log);

    default String mapId(ObjectId id) {
        if(id == null) return null;
        return id.toHexString();
    }

}
