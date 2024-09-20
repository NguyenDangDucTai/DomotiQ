package com.example.devicesservice.services.impls;

import com.example.devicesservice.contexts.AuthCertificate;
import com.example.devicesservice.contexts.SecurityContext;
import com.example.devicesservice.dtos.assistant.AssistantRequest;
import com.example.devicesservice.dtos.assistant.AssistantResponse;
import com.example.devicesservice.models.ModuleUsed;
import com.example.devicesservice.models.MqttMessage;
import com.example.devicesservice.models.User;
import com.example.devicesservice.services.AssistantService;
import com.example.devicesservice.services.MqttService;
import com.example.devicesservice.utils.MapUtils;
import com.example.devicesservice.utils.StringUtils;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.dialogflow.v2.*;
import lombok.RequiredArgsConstructor;
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

    private final double ACCURACY = 0.7;

    private final MqttService mqttService;

    @Override
    public AssistantResponse process(AssistantRequest request) throws IOException {
        AuthCertificate certificate = SecurityContext.getAuthenticationCertificate();
        User user = certificate.getUser();

        Map<String, String> commandParams = resolveCommand(request.getCommand());
        MapUtils.printMap(commandParams);

        if (commandParams.isEmpty()) {
            return printError();
        }

        String responseMessage = commandParams.get("responseMessage");
        String action = commandParams.get("action");
        String device = commandParams.get("device");
        String room = commandParams.get("room");

        String actionCode;
        if(action == null) {
            return printError();
        }
        if(action.equalsIgnoreCase("ON")) {
            actionCode = "1";
        } else if(action.equalsIgnoreCase("OFF")) {
            actionCode = "0";
        } else {
            return printError();
        }

        if(device == null) {
            return printError();
        }

        List<ModuleUsed> modules = user.getDevices().stream()
                .flatMap(d -> d.getModules().stream()
                        .peek(m -> m.setId(d.getDeviceId() + "::" + m.getId())))
                .toList();

        Map<ModuleUsed, Double> compareResult = new HashMap<>();
        modules.forEach(module -> {
            double similarity = StringUtils.compareStrings(device, module.getDisplayNameModule());
            if(similarity >= ACCURACY) {
                compareResult.put(module, similarity);
            }
        });

        if(compareResult.isEmpty()) {
            return printError();
        }

        double maxSimilarity = compareResult.values().stream().max(Double::compare).orElse(0.0);
        List<ModuleUsed> modulesMax = compareResult.entrySet().stream()
                .filter(entry -> entry.getValue() == maxSimilarity)
                .map(Map.Entry::getKey)
                .toList();

        if(modulesMax.size() > 1) {
            return AssistantResponse.builder()
                    .message("Tôi phát hiện có nhiều thiết bị phù hợp. Tôi không thể xác định thiết bị bạn muốn điều khiển. Bạn có thể nói rõ hơn không?")
                    .build();
        }

        ModuleUsed module = modulesMax.get(0);
        String deviceId = module.getId().split("::")[0];

        MqttMessage mqttMessage = MqttMessage.builder()
                .topic(deviceId)
                .message("{module:\"" + module.getId() + "\",cmd:\"SET_STATE\",state:" + actionCode + "}")
                .build();

        mqttService.publish(mqttMessage);

        responseMessage = responseMessage
                .replace("{action}", actionCode.equals("1") ? "Bật" : "Tắt")
                .replace("{device}", module.getDisplayNameModule());

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

    private AssistantResponse printError() {
        return AssistantResponse.builder()
                .message(errorMessageList.get((int) (Math.random() * errorMessageList.size())))
                .build();
    }

    private final List<String> errorMessageList = List.of(
            "Tôi chưa hiểu. Bạn có thể nói lại không?",
            "Xin lỗi, tôi không hiểu ý của bạn. Bạn có thể nói lại không?",
            "Tôi không hiểu bạn muốn gì. Bạn có thể nói rõ hơn không?",
            "Xin lỗi, tôi không hiểu bạn muốn gì. Bạn có thể nói rõ hơn không?"
    );

}
