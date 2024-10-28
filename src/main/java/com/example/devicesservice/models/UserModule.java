package com.example.devicesservice.models;


import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document("user_modules")
public class UserModule {

    @Id
    @EqualsAndHashCode.Include
    private ObjectId id;

    @Transient
    private String _id;

    @DBRef(lazy = true)
    private User user;

    @DBRef(lazy = true)
    private Device device;

    @DBRef(lazy = true)
    private Module module;

    @DBRef(lazy = true)
    private UserRoom room;
    private String roomId;

    private String displayName;

    private UserModuleStatus status;
    private ModuleType type;

}
