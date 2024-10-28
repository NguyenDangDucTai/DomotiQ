package com.example.devicesservice.services.impls;

import com.example.devicesservice.dtos.GetListRequest;
import com.example.devicesservice.dtos.Metadata;
import com.example.devicesservice.dtos.module_log.GetLogListRequest;
import com.example.devicesservice.dtos.module_log.WeatherSensorDataLogListResponse;
import com.example.devicesservice.factories.QueryFactory;
import com.example.devicesservice.mappers.WeatherSensorDataLogMapper;
import com.example.devicesservice.models.*;
import com.example.devicesservice.models.Module;
import com.example.devicesservice.repositories.ModuleLogRepository;
import com.example.devicesservice.services.ModuleService;
import com.example.devicesservice.services.MqttService;
import com.example.devicesservice.services.WeatherSensorModuleService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class WeatherSensorModuleServiceImpl implements WeatherSensorModuleService {

    private final MongoTemplate mongoTemplate;

    private final ModuleService moduleService;
    private final MqttService mqttService;
    private final ModuleLogRepository moduleLogRepository;

    private final WeatherSensorDataLogMapper weatherSensorDataLogMapper;

    @PostConstruct
    private void init() {
        mqttService.subscribe("WEATHER_SENSOR_DATA", this::onReceiveMessage);
    }

    private void onReceiveMessage(MqttMessage message) {
        Map<String, Object> payload = message.getPayload();

        String moduleId = (String) payload.get("module");
        Module module = new WeatherSensorModule(new ObjectId(moduleId));

        WeatherSensorDataLog log = WeatherSensorDataLog.builder()
                .module(module)
                .moduleId(moduleId)
                .timestamp(message.getTimestamp().toEpochSecond())
                .tem((double) payload.get("tem"))
                .hum((double) payload.get("hum"))
                .build();

        moduleLogRepository.save(log);
    }

    @Override
    public WeatherSensorDataLogListResponse getLogs(GetLogListRequest request) {
        Module module = moduleService.findModuleById(request.getModuleId(), ModuleType.WEATHER_SENSOR);

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

        List<WeatherSensorDataLog> logs = mongoTemplate.find(query, WeatherSensorDataLog.class);
        long total = mongoTemplate.count(query.skip(-1).limit(-1), WeatherSensorDataLog.class);

        return WeatherSensorDataLogListResponse.builder()
                .metadata(Metadata.builder().total(total).build())
                .items(logs.stream().map(weatherSensorDataLogMapper::toWeatherSensorDataLogResponse).toList())
                .build();
    }

}
