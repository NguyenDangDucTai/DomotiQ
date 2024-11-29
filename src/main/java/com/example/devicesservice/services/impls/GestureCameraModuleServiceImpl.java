package com.example.devicesservice.services.impls;

import com.example.devicesservice.dtos.modules.gesture_camera.GestureCameraModuleTriggerRequest;
import com.example.devicesservice.dtos.modules.gesture_camera.SetGestureCameraTriggerRequest;
import com.example.devicesservice.exceptions.ValidationException;
import com.example.devicesservice.models.*;
import com.example.devicesservice.models.Module;
import com.example.devicesservice.repositories.GestureCameraModuleRepository;
import com.example.devicesservice.repositories.ModuleLogRepository;
import com.example.devicesservice.services.GestureCameraModuleService;
import com.example.devicesservice.services.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor

public class GestureCameraModuleServiceImpl implements GestureCameraModuleService {

    private final GestureCameraModuleRepository gestureCameraModuleRepository;

    private final ModuleService moduleService;


    @Override
    public GestureCameraModule setTrigger(SetGestureCameraTriggerRequest request) {
        GestureCameraModule module = (GestureCameraModule) moduleService.findModuleById(request.getModuleId(), ModuleType.GESTURE_CAMERA);

        List<GestureCameraModuleTrigger> gestureCameraModuleTriggers = request.getGestureCameraModuleTriggers().stream()
                .map(this::mapGestureCameraModuleTrigger)
                .toList();

        module.setGestureCameraModuleTriggers(gestureCameraModuleTriggers);
        gestureCameraModuleRepository.save(module);

        return module;
    }

    private GestureCameraModuleTrigger mapGestureCameraModuleTrigger(GestureCameraModuleTriggerRequest request) {
        GestureType triggerType = GestureType.valueOf(request.getType());
        GestureCameraModuleTrigger newTrigger = GestureCameraModuleTrigger.builder()
                .type(triggerType)
                .build();

        if(request.getModuleId() == null)
            throw new ValidationException(Map.of("moduleId", "Module id is required"));
        Module m = moduleService.findModuleById(request.getModuleId());
        newTrigger.setModule(m);
        newTrigger.setDevice(m.getDevice());
        if(request.getCommand() == null)
            throw new ValidationException(Map.of("command", "Command is required"));
        if(m.getCommands() == null)
            throw new ValidationException(Map.of("command", "Command is not valid"));
        if(m.getCommands().get(request.getCommand()) == null)
            throw new ValidationException(Map.of("command", "Command is not valid"));
        newTrigger.setCommand(request.getCommand());

        return newTrigger;
    }


}
