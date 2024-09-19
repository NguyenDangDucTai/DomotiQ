package com.example.devicesservice.mappers;

import com.example.devicesservice.dtos.user.UserResponse;
import com.example.devicesservice.models.User;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toUserResponse(User user);

    default String mapId(ObjectId id) {
        return id.toHexString();
    }

}
