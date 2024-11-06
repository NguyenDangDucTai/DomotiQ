package com.example.devicesservice.models;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document("modules")
@ToString
public class Module {

    @Id
    @EqualsAndHashCode.Include
    protected ObjectId id;

    protected ModuleType type;

    @DBRef(lazy = true)
    protected Device device;

    protected String name;

    protected Map<String, Command> commands;

//    protected List<PresenceSensorModuleTrigger> triggerOnDetectPresence;
//    protected List<PresenceSensorModuleTrigger> triggerOnDetectAbsence;

    public Module(ObjectId id) {
        this.id = id;
    }

}
