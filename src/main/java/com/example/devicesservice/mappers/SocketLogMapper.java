package com.example.devicesservice.mappers;

import com.example.devicesservice.dtos.module_log.SocketLogResponse;
import com.example.devicesservice.models.SocketLog;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SocketLogMapper {

    SocketLogResponse toSocketLogResponse(SocketLog log);

    default String mapId(ObjectId id) {
        if(id == null) return null;
        return id.toHexString();
    }

}
