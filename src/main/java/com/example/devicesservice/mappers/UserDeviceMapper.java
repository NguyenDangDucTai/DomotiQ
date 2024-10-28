package com.example.devicesservice.mappers;

import com.example.devicesservice.dtos.user_device.UserDeviceMinResponse;
import com.example.devicesservice.dtos.user_device.UserDeviceResponse;
import com.example.devicesservice.models.UserDevice;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserModuleMapper.class})
public interface UserDeviceMapper {

    UserDeviceMinResponse toUserDeviceMinResponse(UserDevice userDevice);
    UserDeviceResponse toUserDeviceResponse(UserDevice userDevice);

    default String mapId(ObjectId id) {
        return id.toHexString();
    }

}
