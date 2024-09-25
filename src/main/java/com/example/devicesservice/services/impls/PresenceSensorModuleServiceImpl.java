package com.example.devicesservice.services.impls;

import com.example.devicesservice.dtos.GetListRequest;
import com.example.devicesservice.dtos.Metadata;
import com.example.devicesservice.dtos.module_log.GetLogListRequest;
import com.example.devicesservice.dtos.module_log.PresenceSensorDataLogListResponse;
import com.example.devicesservice.dtos.modules.presence_sensor.SetTriggerRequest;
import com.example.devicesservice.factories.QueryFactory;
import com.example.devicesservice.mappers.PresenceSensorDataLogMapper;
import com.example.devicesservice.models.Module;
import com.example.devicesservice.models.*;
import com.example.devicesservice.repositories.ModuleLogRepository;
import com.example.devicesservice.repositories.PresenceSensorModuleRepository;
import com.example.devicesservice.services.ModuleService;
import com.example.devicesservice.services.MqttService;
import com.example.devicesservice.services.PresenceSensorModuleService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class PresenceSensorModuleServiceImpl implements PresenceSensorModuleService {

    private final MongoTemplate mongoTemplate;

    private final PresenceSensorModuleRepository presenceSensorModuleRepository;
    private final ModuleLogRepository moduleLogRepository;

    private final ModuleService moduleService;
    private final MqttService mqttService;

    private final PresenceSensorDataLogMapper presenceSensorDataLogMapper;

    @PostConstruct
    private void init() {
        mqttService.subscribe("PRESENCE_SENSOR_DATA", this::onReceiveMessage);
    }

    private void onReceiveMessage(MqttMessage message) {
        saveLog(message);
        handleTrigger(message);
    }

    private void saveLog(MqttMessage message) {
        Map<String, Object> payload = message.getPayload();

        String moduleId = (String) payload.get("module");
        Module module = new WeatherSensorModule(new ObjectId(moduleId));

        PresenceSensorDataLog log = PresenceSensorDataLog.builder()
                .module(module)
                .timestamp(message.getTimestamp().toEpochSecond())
                .state((int) payload.get("state"))
                .build();

        moduleLogRepository.save(log);

        System.out.println(log.getId().toHexString());
    }

    private void handleTrigger(MqttMessage message) {
//        Map<String, Object> payload = message.getPayload();
//        String moduleId = (String) payload.get("moduleId");
//        Integer state = (Integer) payload.get("state");
//
//        PresenceSensorModule module = findModuleById(moduleId);
//
//        if (state == 1) {
//            PresenceSensorModuleTrigger triggerOnDetectPresence = module.getTriggerOnDetectPresence();
//            if(triggerOnDetectPresence != null) {
//                Set<PresenceSensorModuleTrigger.Action> actions = triggerOnDetectPresence.getActions();
//                executeActions(actions);
//            }
//        } else if(state == 0) {
//            PresenceSensorModuleTrigger triggerOnDetectAbsence = module.getTriggerOnDetectAbsence();
//            if(triggerOnDetectAbsence != null) {
//                Set<PresenceSensorModuleTrigger.Action> actions = triggerOnDetectAbsence.getActions();
//                executeActions(actions);
//            }
//        }
    }

    private void executeActions(Set<PresenceSensorModuleTrigger.Action> actions) {
        if(actions != null && !actions.isEmpty()) {
            actions.forEach(action -> {
                if(action.getType().equals(PresenceSensorModuleTrigger.Action.Type.DEVICE_CONTROL)) {
                    executeDeviceControlAction(action);
                } else if(action.getType().equals(PresenceSensorModuleTrigger.Action.Type.NOTIFICATION)) {
                     executeNotificationAction(action);
                }
            });
        }
    }

    private void executeNotificationAction(PresenceSensorModuleTrigger.Action action) {
    }

    private void executeDeviceControlAction(PresenceSensorModuleTrigger.Action action) {
        PresenceSensorModuleTrigger.DeviceControlAction deviceControlAction = (PresenceSensorModuleTrigger.DeviceControlAction) action;
        Command command = deviceControlAction.getCommand();
        MqttMessage msg = command.toMqttMessage();
        mqttService.publish(msg);
    }

    @Override
    public PresenceSensorModule setTrigger(SetTriggerRequest request) {
        PresenceSensorModule module = findModuleById(request.getModuleId());

        module.setTriggerOnDetectPresence(request.getTriggerOnDetectPresence());
        module.setTriggerOnDetectAbsence(request.getTriggerOnDetectAbsence());

        presenceSensorModuleRepository.save(module);

        return module;
    }

    @Override
    public PresenceSensorDataLogListResponse getLogs(GetLogListRequest request) {
        Module module = moduleService.findModuleById(request.getModuleId(), ModuleType.PRESENCE_SENSOR);

        request.getQueries().add(
                GetListRequest.Query.builder()
                        .field("module")
                        .value(module)
                        .operator(GetListRequest.QueryOperator.EQ)
                        .build()
        );
        request.setSort(
                GetListRequest.Sort.builder()
                        .by("timestamp")
                        .direction(GetListRequest.SortDirection.DESC)
                        .build()
        );

        Query query = QueryFactory.createQuery(request);

        List<PresenceSensorDataLog> logs = mongoTemplate.find(query, PresenceSensorDataLog.class);
        long total = mongoTemplate.count(query.skip(-1).limit(-1), PresenceSensorDataLog.class);

        return PresenceSensorDataLogListResponse.builder()
                .metadata(Metadata.builder().total(total).build())
                .items(logs.stream().map(presenceSensorDataLogMapper::toPresenceSensorDataLogResponse).toList())
                .build();
    }

    private PresenceSensorModule findModuleById(String moduleId) {
        return presenceSensorModuleRepository.findById(new ObjectId(moduleId))
                .orElseThrow(() -> new RuntimeException(String.format("Module with id %s not found", moduleId)));
    }

}
