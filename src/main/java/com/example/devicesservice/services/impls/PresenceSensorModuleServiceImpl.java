package com.example.devicesservice.services.impls;

import com.example.devicesservice.dtos.GetListRequest;
import com.example.devicesservice.dtos.Metadata;
import com.example.devicesservice.dtos.module_log.GetLogListRequest;
import com.example.devicesservice.dtos.module_log.PresenceSensorDataLogListResponse;
import com.example.devicesservice.dtos.modules.presence_sensor.PresenceSensorTriggerRequest;
import com.example.devicesservice.dtos.modules.presence_sensor.SetTriggerRequest;
import com.example.devicesservice.exceptions.ValidationException;
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
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PresenceSensorModuleServiceImpl implements PresenceSensorModuleService {

    private final MongoTemplate mongoTemplate;

    private final PresenceSensorModuleRepository presenceSensorModuleRepository;
    private final ModuleLogRepository moduleLogRepository;

    private final ModuleService moduleService;
    private final MqttService mqttService;
    private final NotificationService notificationService;

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
    }

    private void handleTrigger(MqttMessage message) {
        Map<String, Object> payload = message.getPayload();
        String moduleId = (String) payload.get("module");
        Integer state = (Integer) payload.get("state");
        System.out.println("state trigger: " + state);

        PresenceSensorModule module = (PresenceSensorModule) moduleService.findModuleById(moduleId, ModuleType.PRESENCE_SENSOR);
        System.out.println("module presence: " + module.getId());
        System.out.println("module Id: " +moduleId);
        if (state == 1) {
            List<PresenceSensorModuleTrigger> triggerOnDetectPresence = module.getTriggerOnDetectPresence();
            executeTriggers(state, triggerOnDetectPresence);
        } else if(state == 0) {
            List<PresenceSensorModuleTrigger> triggerOnDetectAbsence = module.getTriggerOnDetectAbsence();
            executeTriggers(state, triggerOnDetectAbsence);
        }
    }

    private void executeTriggers(Integer state, List<PresenceSensorModuleTrigger> triggers) {
        if(triggers != null && !triggers.isEmpty()) {
            triggers.forEach(trigger -> {
                if(trigger.getType() == PresenceSensorModuleTrigger.Type.DEVICE_CONTROL) {
                    executeDeviceControlAction(state, trigger);
                } else if(trigger.getType() == PresenceSensorModuleTrigger.Type.NOTIFICATION) {
                     executeNotificationAction(state, trigger);
                }
            });
        }
    }

    private void executeNotificationAction(Integer state, PresenceSensorModuleTrigger trigger) {
        SendMessage message = SendMessage.builder()
                .chatId("6142006691")
                .text("Presence detected: " + state)
                .build();
        notificationService.sendMessage(message);

        SendMessage messageSecond = SendMessage.builder()
                .chatId("7766710312")
                .text("Presence detected: " + state)
                .build();
        notificationService.sendMessage(messageSecond);
    }

    private void executeDeviceControlAction(Integer state, PresenceSensorModuleTrigger trigger) {
        Module module = trigger.getModule();
        String commandKey = trigger.getCommand();
        if(module.getCommands() == null) return;
        Command command = module.getCommands().get(commandKey);
        if(command == null) return;
        MqttMessage msg = command.toMqttMessage();
        msg.getPayload().put("module", module.getId().toHexString());
        mqttService.publish(msg);
    }

    @Override
    public PresenceSensorModule setTrigger(SetTriggerRequest request) {
        PresenceSensorModule module = (PresenceSensorModule) moduleService.findModuleById(request.getModuleId(), ModuleType.PRESENCE_SENSOR);

        List<PresenceSensorModuleTrigger> triggerOnDetectPresence = request.getTriggerOnDetectPresence().stream()
                .map(this::mapPresenceSensorModuleTrigger)
                .toList();

        List<PresenceSensorModuleTrigger> triggerOnDetectAbsence = request.getTriggerOnDetectAbsence().stream()
                .map(this::mapPresenceSensorModuleTrigger)
                .toList();

        module.setTriggerOnDetectPresence(triggerOnDetectPresence);
        module.setTriggerOnDetectAbsence(triggerOnDetectAbsence);

        presenceSensorModuleRepository.save(module);

        return module;
    }

    private PresenceSensorModuleTrigger mapPresenceSensorModuleTrigger(PresenceSensorTriggerRequest request) {
        PresenceSensorModuleTrigger.Type triggerType = PresenceSensorModuleTrigger.Type.valueOf(request.getType());
        PresenceSensorModuleTrigger newTrigger = PresenceSensorModuleTrigger.builder()
                .type(triggerType)
                .build();

        if(triggerType == PresenceSensorModuleTrigger.Type.DEVICE_CONTROL) {
            if(request.getModuleId() == null)
                throw new ValidationException(Map.of("moduleId", "Module id is required"));
            Module m = moduleService.findModuleById(request.getModuleId());
            newTrigger.setModule(m);

            if(request.getCommand() == null)
                throw new ValidationException(Map.of("command", "Command is required"));
            if(m.getCommands() == null)
                throw new ValidationException(Map.of("command", "Command is not valid"));
            if(m.getCommands().get(request.getCommand()) == null)
                throw new ValidationException(Map.of("command", "Command is not valid"));
            newTrigger.setCommand(request.getCommand());
        } else if(triggerType == PresenceSensorModuleTrigger.Type.NOTIFICATION) {
            if(request.getPhone() == null)
                throw new ValidationException(Map.of("phone", "Phone is required"));
            newTrigger.setPhone(request.getPhone());
        }

        return newTrigger;
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

}
