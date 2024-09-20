package com.example.devicesservice.models;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "users")
public class User {

    @Id
    @EqualsAndHashCode.Include
    private ObjectId id;

    private String username;

    private String password;

    private List<DevicesOwend> devices;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", devices=" + devices +
                '}';
    }

}
