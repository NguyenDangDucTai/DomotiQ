package com.example.devicesservice.repositories;

import com.example.devicesservice.models.Device;
import com.example.devicesservice.models.User;
import com.example.devicesservice.models.UserModule;
import com.example.devicesservice.models.UserRoom;
import com.example.devicesservice.models.Module;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserModuleRepository extends MongoRepository<UserModule, ObjectId> {

    List<UserModule> findAllByUser(User user);
    List<UserModule> findAllByUserAndDevice(User user, Device device);
    List<UserModule> findAllByUserAndRoom(User user, UserRoom room);
    Optional<UserModule> findByUserAndModule(User user, Module id);
    void deleteAllByUserAndDevice(User user, Device device);

}
