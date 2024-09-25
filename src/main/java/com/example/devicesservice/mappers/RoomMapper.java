package com.example.devicesservice.mappers;

import com.example.devicesservice.dtos.user_module.UserModuleMinResponse;
import com.example.devicesservice.dtos.user_room.RoomMinResponse;
import com.example.devicesservice.dtos.user_room.RoomResponse;
import com.example.devicesservice.models.UserModule;
import com.example.devicesservice.models.UserRoom;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

@Mapper(componentModel = "spring")
public abstract class RoomMapper {

    @Autowired
    @Lazy
    protected UserModuleMapper userModuleMapper;

    public abstract RoomMinResponse toRoomMinResponse(UserRoom userRoom);
    public abstract RoomResponse toRoomResponse(UserRoom userRoom);

    protected String mapId(ObjectId id) {
        return id.toHexString();
    }

    protected UserModuleMinResponse mapUserModuleMinResponse(UserModule userModule) {
        return userModuleMapper.toUserModuleMinResponse(userModule);
    }

}
