package com.example.devicesservice.models;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document("user_rooms")
public class UserRoom {

    @Id
    @EqualsAndHashCode.Include
    private ObjectId id;

    @DBRef
    private User user;

    private String name;
    private UserRoomStatus status;

    @Transient
    private List<UserModule> modules;

}
