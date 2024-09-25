package com.example.devicesservice.services.impls;

import com.example.devicesservice.contexts.AuthCertificate;
import com.example.devicesservice.contexts.SecurityContext;
import com.example.devicesservice.dtos.user_room.*;
import com.example.devicesservice.exceptions.NotFoundException;
import com.example.devicesservice.mappers.RoomMapper;
import com.example.devicesservice.models.UserModule;
import com.example.devicesservice.models.UserRoom;
import com.example.devicesservice.models.UserRoomStatus;
import com.example.devicesservice.models.User;
import com.example.devicesservice.models.Module;
import com.example.devicesservice.repositories.RoomRepository;
import com.example.devicesservice.repositories.UserModuleRepository;
import com.example.devicesservice.services.UserRoomService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserRoomServiceImpl implements UserRoomService {

    private final RoomRepository roomRepository;
    private final UserModuleRepository userModuleRepository;

    private final RoomMapper roomMapper;

    @Override
    public RoomListResponse getRoomListByUser(GetRoomListByUserRequest request) {
        AuthCertificate certificate = SecurityContext.getAuthenticationCertificate();
        User user = certificate.getUser();

        List<UserRoom> userRooms = roomRepository.findAllByUser(user);

        return RoomListResponse.builder()
                .rooms(userRooms.stream().map(roomMapper::toRoomMinResponse).toList())
                .build();
    }

    @Override
    public RoomResponse getRoomById(GetRoomByIdRequest request) {
        AuthCertificate certificate = SecurityContext.getAuthenticationCertificate();
        User user = certificate.getUser();

        UserRoom userRoom = findRoomById(request.getId());
        List<UserModule> userModules = userModuleRepository.findAllByUserAndRoom(user, userRoom);
        userRoom.setModules(userModules);

        return roomMapper.toRoomResponse(userRoom);
    }

    @Transactional
    @Override
    public RoomResponse createRoom(CreateRoomRequest request) {
        AuthCertificate certificate = SecurityContext.getAuthenticationCertificate();
        User user = certificate.getUser();

        UserRoom userRoom = UserRoom.builder()
                .user(user)
                .name(request.getName())
                .status(UserRoomStatus.valueOf(request.getStatus()))
                .build();

        roomRepository.save(userRoom);

        if(request.getModuleIds() != null && !request.getModuleIds().isEmpty()) {
            List<UserModule> modules = request.getModuleIds().stream()
                    .map(moduleId -> {
                        UserModule userModule = userModuleRepository.findByUserAndModule(user, new Module(new ObjectId(moduleId)))
                                .orElseThrow(() -> new NotFoundException(String.format("module with id %s not found", moduleId)));
                        userModule.setRoom(userRoom);
                        userModule.setRoomId(userRoom.getId().toHexString());
                        return userModule;
                    })
                    .toList();
            userRoom.setModules(modules);
            userModuleRepository.saveAll(modules);
        }

        return roomMapper.toRoomResponse(userRoom);
    }

    @Transactional
    @Override
    public RoomResponse updateRoom(UpdateRoomRequest request) {
        AuthCertificate certificate = SecurityContext.getAuthenticationCertificate();
        User user = certificate.getUser();

        UserRoom userRoom = findRoomById(request.getId());

        userRoom.setName(request.getName());
        userRoom.setStatus(UserRoomStatus.valueOf(request.getStatus()));

        roomRepository.save(userRoom);

        List<UserModule> modules = userModuleRepository.findAllByUserAndRoom(user, userRoom);
        modules.forEach(module -> {
            module.setRoom(null);
            module.setRoomId(null);
        });
        userModuleRepository.saveAll(modules);

        if(request.getModuleIds() != null) {
            List<UserModule> newModules = request.getModuleIds().stream()
                    .map(moduleId -> {
                        UserModule userModule = userModuleRepository.findByUserAndModule(user, new Module(new ObjectId(moduleId)))
                                .orElseThrow(() -> new NotFoundException(String.format("module with id %s not found", moduleId)));
                        userModule.setRoom(userRoom);
                        userModule.setRoomId(userRoom.getId().toHexString());
                        return userModule;
                    })
                    .toList();
            userRoom.setModules(newModules);

            userModuleRepository.saveAll(newModules);
        }

        return roomMapper.toRoomResponse(userRoom);
    }

    @Transactional
    @Override
    public String deleteRoom(DeleteRoomRequest request) {
        AuthCertificate certificate = SecurityContext.getAuthenticationCertificate();
        User user = certificate.getUser();

        UserRoom userRoom = findRoomById(request.getId());

        List<UserModule> modules = userModuleRepository.findAllByUserAndRoom(user, userRoom);
        modules.forEach(module -> {
            module.setRoom(null);
            module.setRoomId(null);
        });
        userModuleRepository.saveAll(modules);

        roomRepository.delete(userRoom);

        return request.getId();
    }

    @Override
    public UserRoom findRoomById(String id) {
        return roomRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new NotFoundException(String.format("room with id %s not found", id)));
    }

}
