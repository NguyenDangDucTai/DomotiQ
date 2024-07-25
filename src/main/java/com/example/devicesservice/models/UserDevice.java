package com.example.devicesservice.models;

import com.example.devicesservice.enums.DeviceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "userDevices")
public class Device {

    @Id
    private String id;
    private String name;
    private String userId;
    private String type;
    private String location;

}
