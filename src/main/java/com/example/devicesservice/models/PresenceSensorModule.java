package com.example.devicesservice.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Document(collection = "modules")
public class PresenceSensorModule extends Module {

    private PresenceSensorModuleTrigger triggerOnDetectPresence;
    private PresenceSensorModuleTrigger triggerOnDetectAbsence;

    public PresenceSensorModule() {
        this.type = ModuleType.PRESENCE_SENSOR;
    }

}
