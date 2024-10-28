package com.example.devicesservice.mappers;

import com.example.devicesservice.dtos.device.DeviceMinResponse;
import com.example.devicesservice.dtos.device.DeviceResponse;
import com.example.devicesservice.models.Device;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeviceMapper {

    DeviceMinResponse toDeviceMinResponse(Device device);
    DeviceResponse toDeviceResponse(Device device);

    default String mapId(ObjectId id) {
        if(id == null) return null;
        return id.toHexString();
    }

}
