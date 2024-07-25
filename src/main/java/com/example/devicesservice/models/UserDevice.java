package com.example.devicesservice.models;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Data
@Document(collection = "userDevices")
public class UserDevice {

    @Id
    private String deviceId;
    private String deviceName;
    private String userId;
    private String type;
    private String location;


}
