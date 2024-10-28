package com.example.devicesservice.repositories;

import com.example.devicesservice.models.UserRoom;
import com.example.devicesservice.models.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends MongoRepository<UserRoom, ObjectId> {

    List<UserRoom> findAllByUser(User user);

}
