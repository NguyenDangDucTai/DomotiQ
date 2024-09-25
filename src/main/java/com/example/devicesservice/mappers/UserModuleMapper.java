package com.example.devicesservice.mappers;

import com.example.devicesservice.dtos.user_module.UserModuleMinResponse;
import com.example.devicesservice.dtos.user_module.UserModuleResponse;
import com.example.devicesservice.models.Device;
import com.example.devicesservice.models.UserModule;
import com.example.devicesservice.models.Module;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {RoomMapper.class})
public abstract class UserModuleMapper {

    @Mapping(target = "deviceId", expression = "java(mapDeviceId(userModule.getDevice()))")
    @Mapping(target = "moduleId", expression = "java(mapModuleId(userModule.getModule()))")
    public abstract UserModuleMinResponse toUserModuleMinResponse(UserModule userModule);

    public abstract UserModuleResponse toUserModuleResponse(UserModule userModule);

    protected String mapDeviceId(Device device) {
        if(device == null) return null;
        if(device.getId() == null) return null;
        return device.getId().toHexString();
    }

    protected String mapModuleId(Module module) {
        if(module == null) return null;
        if(module.getId() == null) return null;
        return module.getId().toHexString();
    }

}
