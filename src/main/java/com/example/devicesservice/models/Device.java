package com.example.devicesservice.models;


import com.example.devicesservice.models.enums.DeviceStatus;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@Document(collection = "devices")
public class Device {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ObjectId id;

    private String productId;

    private String name;

    private List<ModuleDevice> module;

}
