package com.example.devicesservice.services.impls;

import com.example.devicesservice.dtos.device.*;
import com.example.devicesservice.exceptions.NotFoundException;
import com.example.devicesservice.factories.ModuleFactory;
import com.example.devicesservice.mappers.DeviceMapper;
import com.example.devicesservice.models.Device;
import com.example.devicesservice.models.DeviceType;
import com.example.devicesservice.models.Command;
import com.example.devicesservice.models.Module;
import com.example.devicesservice.models.ModuleType;
import com.example.devicesservice.repositories.DeviceRepository;
import com.example.devicesservice.repositories.ModuleRepository;
import com.example.devicesservice.services.DeviceService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;
    private final ModuleRepository moduleRepository;
    private final DeviceMapper deviceMapper;

    @Override
    public DeviceListResponse getDeviceList(GetDeviceListRequest request) {
        List<Device> devices = deviceRepository.findAll();
        return DeviceListResponse.builder()
                .items(devices.stream().map(deviceMapper::toDeviceMinResponse).toList())
                .build();
    }

    @Override
    public DeviceResponse getDeviceById(GetDeviceByIdRequest request) {
        Device device = findDeviceById(request.getId());
        List<Module> modules = moduleRepository.findAllByDevice(device);
        device.setModules(modules);
        return deviceMapper.toDeviceResponse(device);
    }

    @Transactional
    @Override
    public DeviceResponse createDevice(CreateDeviceRequest request) {
        Device device = Device.builder()
                .type(DeviceType.valueOf(request.getType()))
                .name(request.getName())
                .build();

        List<Module> modules = request.getModules().stream()
                .map(m -> {
                    ModuleType type = ModuleType.valueOf(m.getType());
                    Module module = ModuleFactory.createModule(type);
                    module.setDevice(device);
                    module.setName(m.getName());

                    if (m.getCommands() != null) {
                        Map<String, Command> commands = new HashMap<>();
                        m.getCommands().forEach(cmd -> {
                            Command command = Command.builder()
                                    .name(cmd.getName())
                                    .params(cmd.getParams())
                                    .build();
                            commands.put(command.getName(), command);
                        });
                        module.setCommands(commands);
                    }

                    return module;
                })
                .toList();
        device.setModules(modules);

        deviceRepository.save(device);
        moduleRepository.saveAll(modules);

        return deviceMapper.toDeviceResponse(device);
    }

    @Transactional
    @Override
    public DeviceResponse updateDevice(UpdateDeviceRequest request) {
        Device device = findDeviceById(request.getId());

        device.setType(DeviceType.valueOf(request.getType()));
        device.setName(request.getName());

        List<Module> modules = request.getModules().stream()
                .map(m -> {
                    ModuleType type = ModuleType.valueOf(m.getType());
                    Module module = ModuleFactory.createModule(type);
                    module.setId(m.getId() == null ? null : new ObjectId(m.getId()));
                    module.setDevice(device);
                    module.setName(m.getName());

                    if (m.getCommands() != null) {
                        Map<String, Command> commands = new HashMap<>();
                        m.getCommands().forEach(cmd -> {
                            Command command = Command.builder()
                                    .id(cmd.getId() == null ? null : new ObjectId(cmd.getId()))
                                    .name(cmd.getName())
                                    .params(cmd.getParams())
                                    .build();
                            commands.put(command.getName(), command);
                        });
                        module.setCommands(commands);
                    }

                    return module;
                })
                .toList();
        device.setModules(modules);

        deviceRepository.save(device);
        moduleRepository.deleteAllByDevice(device);
        moduleRepository.saveAll(modules);

        return deviceMapper.toDeviceResponse(device);
    }

    @Transactional
    @Override
    public String deleteDevice(DeleteDeviceRequest request) {
        Device device = findDeviceById(request.getId());
        moduleRepository.deleteAllByDevice(device);
        deviceRepository.delete(device);
        return request.getId();
    }

    @Override
    public Device findDeviceById(String deviceId) {
        return deviceRepository.findById(new ObjectId(deviceId))
                .orElseThrow(() -> new NotFoundException(String.format("Device with id %s not found", deviceId)));
    }

}
