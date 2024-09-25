package com.example.devicesservice.services.impls;

import com.example.devicesservice.contexts.AuthCertificate;
import com.example.devicesservice.contexts.SecurityContext;
import com.example.devicesservice.dtos.GetListRequest;
import com.example.devicesservice.dtos.Metadata;
import com.example.devicesservice.dtos.user_module.*;
import com.example.devicesservice.exceptions.NotFoundException;
import com.example.devicesservice.factories.QueryFactory;
import com.example.devicesservice.mappers.UserModuleMapper;
import com.example.devicesservice.models.Module;
import com.example.devicesservice.models.*;
import com.example.devicesservice.repositories.UserModuleRepository;
import com.example.devicesservice.services.UserModuleService;
import com.example.devicesservice.services.UserRoomService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserModuleServiceImpl implements UserModuleService {

    private final MongoTemplate mongoTemplate;
    private final UserModuleRepository userModuleRepository;
    private final UserRoomService userRoomService;
    private final UserModuleMapper userModuleMapper;

    @Override
    public UserModuleListResponse getModuleListByUser(GetModuleListByUserRequest request) {
        AuthCertificate certificate = SecurityContext.getAuthenticationCertificate();
        User user = certificate.getUser();

        request.getQueries().add(
                GetListRequest.Query.builder()
                        .field("user")
                        .value(user)
                        .operator(GetListRequest.QueryOperator.EQ)
                        .build()
        );
        Query query = QueryFactory.createQuery(request);

        List<UserModule> userModules = mongoTemplate.find(query, UserModule.class);
        long total = mongoTemplate.count(query.skip(-1).limit(-1), UserModule.class);

        return UserModuleListResponse.builder()
                .metadata(Metadata.builder().total(total).build())
                .items(userModules.stream().map(userModuleMapper::toUserModuleMinResponse).toList())
                .build();
    }

    @Override
    public UserModuleResponse getModuleFromUserById(GetModuleFromUserByIdRequest request) {
        AuthCertificate certificate = SecurityContext.getAuthenticationCertificate();
        User user = certificate.getUser();

        UserModule userModule = userModuleRepository.findByUserAndModule(user, new Module(new ObjectId(request.getModuleId())))
                .orElseThrow(() -> new NotFoundException(String.format("module with moduleId %s not found", request.getModuleId())));

        return userModuleMapper.toUserModuleResponse(userModule);
    }

    @Transactional
    @Override
    public UserModuleResponse updateModuleFromUser(UpdateModuleFromUserRequest request) {
        AuthCertificate certificate = SecurityContext.getAuthenticationCertificate();
        User user = certificate.getUser();

        UserModule userModule = userModuleRepository.findByUserAndModule(user, new Module(new ObjectId(request.getModuleId())))
                .orElseThrow(() -> new NotFoundException(String.format("module with userId %s and moduleId %s not found", user.getId(), request.getModuleId())));

        userModule.setDisplayName(request.getDisplayName());
        userModule.setStatus(UserModuleStatus.valueOf(request.getStatus()));

        UserRoom userRoom = null;
        if(request.getRoomId() != null) {
            userRoom = userRoomService.findRoomById(request.getRoomId());
        }
        userModule.setRoom(userRoom);
        userModule.setRoomId(userRoom != null ? userRoom.getId().toHexString() : null);

        userModuleRepository.save(userModule);

        return userModuleMapper.toUserModuleResponse(userModule);
    }

}
