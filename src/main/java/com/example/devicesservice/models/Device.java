package com.example.devicesservice.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "devices")
public class Device {

    @Id
    @EqualsAndHashCode.Include
    private ObjectId id;

    private DeviceType type;

    private String name;

    @Transient
    private List<Module> modules;

    public Device(ObjectId id) {
        this.id = id;
    }

}
