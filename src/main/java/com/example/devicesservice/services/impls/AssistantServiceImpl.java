package com.example.devicesservice.services.impls;

import com.example.devicesservice.contexts.AuthCertificate;
import com.example.devicesservice.contexts.SecurityContext;
import com.example.devicesservice.dtos.assistant.AssistantRequest;
import com.example.devicesservice.dtos.assistant.AssistantResponse;
import com.example.devicesservice.models.MqttMessage;
import com.example.devicesservice.models.User;
import com.example.devicesservice.models.UserModule;
import com.example.devicesservice.repositories.UserModuleRepository;
import com.example.devicesservice.services.AssistantService;
import com.example.devicesservice.services.MqttService;
import com.example.devicesservice.utils.MapUtils;
import com.example.devicesservice.utils.StringUtils;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.dialogflow.v2.*;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AssistantServiceImpl implements AssistantService {

    @Value("${dialogflow.project.id}")
    private String PROJECT_ID;

    @Value("${cloud.storage.credentials-file}")
    private String CREDENTIALS_FILE;

    private final double ACCURACY = 0.6;

    private final MqttService mqttService;

    private final UserModuleRepository userModuleRepository;

    @Override
    public AssistantResponse process(AssistantRequest request) throws IOException {
        AuthCertificate certificate = SecurityContext.getAuthenticationCertificate();
        User user = certificate.getUser();

        Map<String, String> commandParams = resolveCommand(request.getCommand());
        MapUtils.printMap(commandParams);

        if (commandParams.isEmpty()) {
            return printError(ErrorType.INVALID_COMMAND);
        }
        System.out.println("1");

        String responseMessage = commandParams.get("responseMessage");
        String action = commandParams.get("action");
        String device = commandParams.get("device");
        String room = commandParams.get("room");

        System.out.println("2");
        int actionCode;
        if(action == null) {
            return printError(ErrorType.INVALID_COMMAND);
        } System.out.println("3");
        if(action.equalsIgnoreCase("ON")) {
            actionCode = 1;
        } else if(action.equalsIgnoreCase("OFF")) {
            actionCode = 0;
        } else {
            return printError(ErrorType.INVALID_COMMAND);
        }

        System.out.println("4");

        if(device == null) {
            return printError(ErrorType.INVALID_DEVICE);
        }

        List<UserModule> modules = userModuleRepository.findAllByUser(user).stream()
                .peek(m -> m.set_id(String.format("%s::%s", m.getDevice().getId().toHexString(), m.getModule().getId().toHexString())))
                .toList();
        System.out.println("lits module" + modules.size());

        Map<UserModule, Double> compareResult = new HashMap<>();
        modules.forEach(module -> {
            System.out.println(device);
            System.out.println("module: " + module.getDisplayName());
            double similarity = StringUtils.compareStrings(device, module.getDisplayName());
            if(similarity >= ACCURACY) {
                compareResult.put(module, similarity);
            }
        });

        if(compareResult.isEmpty()) {
            return printError(ErrorType.INVALID_DEVICE);
        }

        double maxSimilarity = compareResult.values().stream().max(Double::compare).orElse(0.0);
        double finalMaxSimilarity1 = maxSimilarity;
        List<UserModule> modulesMax = compareResult.entrySet().stream()
                .filter(entry -> entry.getValue() == finalMaxSimilarity1)
                .map(Map.Entry::getKey)
                .toList();

        if(modulesMax.size() > 1) {
            compareResult.clear();
            modulesMax.forEach(module -> {
                double similarity = StringUtils.compareLength(device, module.getDisplayName());
                if(similarity >= ACCURACY) {
                    compareResult.put(module, similarity);
                }
            });

            if(compareResult.isEmpty()) {
                return printError(ErrorType.MULTIPLE_DEVICES);
            }

            maxSimilarity = compareResult.values().stream().max(Double::compare).orElse(0.0);
            double finalMaxSimilarity2 = maxSimilarity;
            modulesMax = compareResult.entrySet().stream()
                    .filter(entry -> entry.getValue() == finalMaxSimilarity2)
                    .map(Map.Entry::getKey)
                    .toList();

            if(modulesMax.size() > 1) {
                return printError(ErrorType.MULTIPLE_DEVICES);
            }
        }

        UserModule module = modulesMax.get(0);
        String deviceId = module.get_id().split("::")[0];

        Map<String, Object> payload = new HashMap<>();
        payload.put("module", module.getModule().getId().toHexString());
        payload.put("cmd", "SET_STATE");
        payload.put("state", actionCode);

        MqttMessage mqttMessage = MqttMessage.builder()
                .topic(deviceId)
                .payload(payload)
                .build();

        mqttService.publish(mqttMessage);

        responseMessage = responseMessage
                .replace("{action}", actionCode == 1 ? "Bật" : "Tắt")
                .replace("{device}", module.getDisplayName());

        return AssistantResponse.builder()
                .message(responseMessage)
                .build();
    }

    private Map<String, String> resolveCommand(String command) throws IOException {
        AuthCertificate certificate = SecurityContext.getAuthenticationCertificate();
        User user = certificate.getUser();
        String sessionId = user.getId().toHexString();

        InputStream credentialsStream = new ClassPathResource(CREDENTIALS_FILE).getInputStream();
        GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream);

        SessionsSettings sessionsSettings = SessionsSettings.newBuilder()
                .setCredentialsProvider(() -> credentials)
                .build();

        try (SessionsClient sessionsClient = SessionsClient.create(sessionsSettings)) {
            SessionName session = SessionName.of(PROJECT_ID, sessionId);

            TextInput.Builder textInput = TextInput.newBuilder()
                    .setText(command)
                    .setLanguageCode("vi");

            QueryInput queryInput = QueryInput.newBuilder()
                    .setText(textInput)
                    .build();

            DetectIntentResponse response = sessionsClient.detectIntent(session, queryInput);

            QueryResult queryResult = response.getQueryResult();

            Map<String, com.google.protobuf.Value> queryResultMap = queryResult.getParameters().getFieldsMap();

            Map<String, String> result = new HashMap<>();
            result.put("responseMessage", queryResult.getFulfillmentText());

            queryResultMap.forEach((key, value) -> {
                result.put(key, value.getStringValue());
            });

            return result;
        }
    }

    private AssistantResponse printError(ErrorType type) {
        switch (type) {
            case INVALID_DEVICE -> {
                return AssistantResponse.builder()
                        .message("Tôi không tìm thấy thiết bị phù hợp. Bạn có thể nói rõ hơn không?")
                        .build();
            }
            case MULTIPLE_DEVICES -> {
                return AssistantResponse.builder()
                        .message("Tôi phát hiện có nhiều thiết bị phù hợp. Tôi không thể xác định thiết bị bạn muốn điều khiển. Bạn có thể nói rõ hơn không?")
                        .build();
            }
            default -> {
                return AssistantResponse.builder()
                        .message(errorMessageList.get((int) (Math.random() * errorMessageList.size())))
                        .build();
            }
        }
    }

    private final List<String> errorMessageList = List.of(
            "Tôi chưa hiểu. Bạn có thể nói lại không?",
            "Xin lỗi, tôi không hiểu ý của bạn. Bạn có thể nói lại không?",
            "Tôi không hiểu bạn muốn gì. Bạn có thể nói rõ hơn không?",
            "Xin lỗi, tôi không hiểu bạn muốn gì. Bạn có thể nói rõ hơn không?"
    );

    enum ErrorType {
        INVALID_COMMAND,
        INVALID_DEVICE,
        MULTIPLE_DEVICES,
    }

}
