package com.example.devicesservice.services.impls;

import com.example.devicesservice.contexts.AuthCertificate;
import com.example.devicesservice.contexts.SecurityContext;
import com.example.devicesservice.dtos.user_device.*;
import com.example.devicesservice.dtos.user_module.AddModuleToUserRequest;
import com.example.devicesservice.exceptions.NotFoundException;
import com.example.devicesservice.mappers.UserDeviceMapper;
import com.example.devicesservice.models.*;
import com.example.devicesservice.models.Module;
import com.example.devicesservice.repositories.ModuleRepository;
import com.example.devicesservice.repositories.UserDeviceRepository;
import com.example.devicesservice.repositories.UserModuleRepository;
import com.example.devicesservice.services.DeviceService;
import com.example.devicesservice.services.UserDeviceService;
import com.example.devicesservice.services.UserRoomService;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@AllArgsConstructor
public class UserDeviceServiceImpl implements UserDeviceService {

    private final ModuleRepository moduleRepository;
    private final UserDeviceRepository userDeviceRepository;
    private final UserModuleRepository userModuleRepository;

    private final DeviceService deviceService;

    private final UserDeviceMapper userDeviceMapper;
    private final UserRoomService userRoomService;

    @Override
    public UserDeviceListResponse getDeviceListByUser(GetDeviceListByUserRequest request) {
        AuthCertificate certificate = SecurityContext.getAuthenticationCertificate();
        User user = certificate.getUser();

        List<UserDevice> userDevices = userDeviceRepository.findAllByUser(user);

        return UserDeviceListResponse.builder()
                .items(userDevices.stream().map(userDeviceMapper::toUserDeviceMinResponse).toList())
                .build();
    }

    @Override
    public UserDeviceResponse getDeviceFromUserById(GetDeviceFromUserByIdRequest request) {
        AuthCertificate certificate = SecurityContext.getAuthenticationCertificate();
        User user = certificate.getUser();

        Device device = new Device(new ObjectId(request.getDeviceId()));
        UserDevice userDevice = userDeviceRepository.findByUserAndDevice(user, device)
                .orElseThrow(() -> new NotFoundException(String.format("device with userId: %s and deviceId: %s not found", user.getId(), device.getId().toHexString())));

        List<UserModule> userModules = userModuleRepository.findAllByUserAndDevice(user, device);
        userDevice.setModules(userModules);

        return userDeviceMapper.toUserDeviceResponse(userDevice);
    }

    @Transactional
    @Override
    public UserDeviceResponse addDeviceToUser(AddDeviceToUserRequest request) {
        AuthCertificate certificate = SecurityContext.getAuthenticationCertificate();
        User user = certificate.getUser();

        Device device = deviceService.findDeviceById(request.getDeviceId());
        List<Module> modulesOfDevice = moduleRepository.findAllByDevice(device);

        List<UserModule> userModules = modulesOfDevice.stream()
                .map(module -> {
                    AddModuleToUserRequest addModuleRequest = request.getModules().stream()
                            .filter(m -> m.getModuleId().equals(module.getId().toHexString()))
                            .findFirst()
                            .orElseThrow(() -> new NotFoundException(String.format("Module with id %s not found", module.getId())));
                    UserRoom userRoom = null;
                    if(addModuleRequest.getRoomId() != null) {
                        userRoom = userRoomService.findRoomById(addModuleRequest.getRoomId());
                    }
                    return UserModule.builder()
                            .user(user)
                            .device(device)
                            .module(module)
                            .displayName(addModuleRequest.getDisplayName())
                            .room(userRoom)
                            .roomId(addModuleRequest.getRoomId())
                            .type(ModuleType.valueOf(addModuleRequest.getType()))
                            .status(UserModuleStatus.valueOf(addModuleRequest.getStatus()))
                            .build();
                })
                .toList();

        UserDevice userDevice = UserDevice.builder()
                .user(user)
                .device(device)
                .modules(userModules)
                .build();

        userDeviceRepository.save(userDevice);
        userModuleRepository.saveAll(userModules);

        return userDeviceMapper.toUserDeviceResponse(userDevice);
    }

    @Transactional
    @Override
    public UserDeviceResponse updateDeviceFromUser(UpdateDeviceFromUserRequest request) {
        AuthCertificate certificate = SecurityContext.getAuthenticationCertificate();
        User user = certificate.getUser();

        Device device = new Device(new ObjectId(request.getDeviceId()));
        UserDevice userDevice = userDeviceRepository.findByUserAndDevice(user, device)
                .orElseThrow(() -> new NotFoundException(String.format("device with userId: %s and deviceId: %s not found", user.getId(), device.getId().toHexString())));

        List<UserModule> userModules = userModuleRepository.findAllByUserAndDevice(user, device);
        userDevice.setModules(userModules);

        userDeviceRepository.save(userDevice);

        return userDeviceMapper.toUserDeviceResponse(userDevice);
    }

    @Transactional
    @Override
    public String removeDeviceFromUser(RemoveDeviceFromUserRequest request) {
        AuthCertificate certificate = SecurityContext.getAuthenticationCertificate();
        User user = certificate.getUser();

        Device device = new Device(new ObjectId(request.getDeviceId()));
        userDeviceRepository.findByUserAndDevice(user, device)
                .orElseThrow(() -> new NotFoundException(String.format("device with userId: %s and deviceId: %s not found", user.getId(), device.getId().toHexString())));

        userDeviceRepository.deleteByUserAndDevice(user, device);
        userModuleRepository.deleteAllByUserAndDevice(user, device);

        return request.getDeviceId();
    }

}
