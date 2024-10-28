package com.example.devicesservice.services.impls;

import com.example.devicesservice.dtos.user.UserResponse;
import com.example.devicesservice.exceptions.NotFoundException;
import com.example.devicesservice.mappers.UserMapper;
import com.example.devicesservice.models.User;
import com.example.devicesservice.repositories.UserRepository;
import com.example.devicesservice.services.UserService;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponse getUserById(String id) {
        User user = userRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new NotFoundException(String.format("user with id %s not found", id)));
        return userMapper.toUserResponse(user);
    }

}
