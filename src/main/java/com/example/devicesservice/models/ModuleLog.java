package com.example.devicesservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document("module_logs")
public class ModuleLog {

    @Id
    @EqualsAndHashCode.Include
    private ObjectId id;

    @DBRef(lazy = true)
    private Module module;
    private String moduleId;

    private Long timestamp;

}
