package com.example.devicesservice.services.impls;

import com.example.devicesservice.dtos.modules.ModulePresenceByIdResponse;
import com.example.devicesservice.dtos.modules.ModuleResponse;
import com.example.devicesservice.dtos.modules.gesture_camera.GestureCameraModuleTriggerResponse;
import com.example.devicesservice.dtos.modules.presence_sensor.PresenceSensorModuleTriggerResponse;
import com.example.devicesservice.exceptions.NotFoundException;
import com.example.devicesservice.models.Module;
import com.example.devicesservice.models.ModuleType;
import com.example.devicesservice.models.PresenceSensorModuleTrigger;
import com.example.devicesservice.repositories.ModuleRepository;
import com.example.devicesservice.services.ModuleService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRepository;

    @Override
    public Module findModuleById(String id) {
        return moduleRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new NotFoundException(String.format("Module with id %s not found", id)));
    }

    @Override
    public Module findModuleById(String id, ModuleType type) {
        return moduleRepository.findByIdAndType(new ObjectId(id), type)
                .orElseThrow(() -> new NotFoundException(String.format("Module with id %s not found", id)));
    }

    @Override
    public ModulePresenceByIdResponse findModulePresenceById(String id) {
        ModuleResponse moduleResponse = moduleRepository.findModulePresenceById(new ObjectId(id));
        if(moduleResponse.getType().equals(ModuleType.PRESENCE_SENSOR)){
            List<PresenceSensorModuleTriggerResponse> triggerOnDetectPresence = moduleResponse.getTriggerOnDetectPresence().stream()
                    .map(m ->{
                        String moduleId = null;
                        if(m.getType().equals(PresenceSensorModuleTrigger.Type.DEVICE_CONTROL)){
                            moduleId = String.valueOf(m.getModule().getId());
                        }
                        PresenceSensorModuleTriggerResponse presenceSensorModuleTriggerResponse = PresenceSensorModuleTriggerResponse.builder()
                                .moduleId(moduleId)
                                .phone(m.getPhone())
                                .type(m.getType().toString())
                                .command(m.getCommand())
                                .build();
                        return presenceSensorModuleTriggerResponse;
                    }).toList();
            List<PresenceSensorModuleTriggerResponse> triggerOnDetectAbsence = moduleResponse.getTriggerOnDetectAbsence().stream()
                    .map(m ->{
                        String moduleId = null;
                        if(m.getType().equals(PresenceSensorModuleTrigger.Type.DEVICE_CONTROL)){
                            moduleId = String.valueOf(m.getModule().getId());
                        }
                        PresenceSensorModuleTriggerResponse presenceSensorModuleTriggerResponse = PresenceSensorModuleTriggerResponse.builder()
                                .moduleId(moduleId)
                                .phone(m.getPhone())
                                .type(m.getType().toString())
                                .command(m.getCommand())
                                .build();
                        return presenceSensorModuleTriggerResponse;
                    }).toList();
            return ModulePresenceByIdResponse.builder()
                    .id(moduleResponse.getId())
                    .name(moduleResponse.getName())
                    .type(moduleResponse.getType())
                    .triggerOnDetectPresence(triggerOnDetectPresence)
                    .triggerOnDetectAbsence(triggerOnDetectAbsence)
                    .build();
        }
        else if(moduleResponse.getType().equals(ModuleType.GESTURE_CAMERA)) {
            System.out.println(moduleResponse);
            List<GestureCameraModuleTriggerResponse> gestureCameraModuleTrigger = moduleResponse.getGestureCameraModuleTriggers().stream()
                    .map(m->{
                        String moduleId = String.valueOf(m.getModule().getId());
                        GestureCameraModuleTriggerResponse gestureCameraModuleTriggerResponse = GestureCameraModuleTriggerResponse.builder()
                                .moduleId(moduleId)
                                .type(m.getType().toString())
                                .command(m.getCommand())
                                .build();
                        return gestureCameraModuleTriggerResponse;
                    }).toList();
            return ModulePresenceByIdResponse.builder()
                    .id(moduleResponse.getId())
                    .name(moduleResponse.getName())
                    .type(moduleResponse.getType())
                    .gestureCameraModuleTrigger(gestureCameraModuleTrigger)
                    .build();
        }

        return null;
    }

}
