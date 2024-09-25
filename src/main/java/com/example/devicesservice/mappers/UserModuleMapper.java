package com.example.devicesservice.mappers;

import com.example.devicesservice.dtos.user_module.UserModuleMinResponse;
import com.example.devicesservice.dtos.user_module.UserModuleResponse;
import com.example.devicesservice.models.Device;
import com.example.devicesservice.models.UserModule;
import com.example.devicesservice.models.Module;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {RoomMapper.class})
public interface UserModuleMapper {

    @Mapping(target = "deviceId", source = "device")
    @Mapping(target = "moduleId", source = "module")
    UserModuleMinResponse toUserModuleMinResponse(UserModule userModule);

    @Mapping(target = "deviceId", source = "device")
    @Mapping(target = "moduleId", source = "module")
    UserModuleResponse toUserModuleResponse(UserModule userModule);

    default String mapDeviceId(Device device) {
        if(device == null) return null;
        if(device.getId() == null) return null;
        return device.getId().toHexString();
    }

    default String mapModuleId(Module module) {
        if(module == null) return null;
        if(module.getId() == null) return null;
        return module.getId().toHexString();
    }

}
