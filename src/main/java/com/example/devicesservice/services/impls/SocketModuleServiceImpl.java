package com.example.devicesservice.services.impls;

import com.example.devicesservice.dtos.GetListRequest;
import com.example.devicesservice.dtos.Metadata;
import com.example.devicesservice.dtos.module_log.GetLogListRequest;
import com.example.devicesservice.dtos.module_log.SocketLogListResponse;
import com.example.devicesservice.factories.QueryFactory;
import com.example.devicesservice.mappers.SocketLogMapper;
import com.example.devicesservice.models.Module;
import com.example.devicesservice.models.ModuleType;
import com.example.devicesservice.models.SocketLog;
import com.example.devicesservice.services.ModuleService;
import com.example.devicesservice.services.SocketModuleService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SocketModuleServiceImpl implements SocketModuleService {

    private final MongoTemplate mongoTemplate;

    private final ModuleService moduleService;

    private final SocketLogMapper socketLogMapper;

    @Override
    public SocketLogListResponse getLogs(GetLogListRequest request) {
        Module module = moduleService.findModuleById(request.getModuleId(), ModuleType.SOCKET);

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

        List<SocketLog> logs = mongoTemplate.find(query, SocketLog.class);
        long total = mongoTemplate.count(query.skip(-1).limit(-1), SocketLog.class);

        return SocketLogListResponse.builder()
                .metadata(Metadata.builder().total(total).build())
                .items(logs.stream().map(socketLogMapper::toSocketLogResponse).toList())
                .build();
    }

}
