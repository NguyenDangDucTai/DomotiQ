package com.example.devicesservice.repositories;

import com.example.devicesservice.models.Device;
import com.example.devicesservice.models.User;
import com.example.devicesservice.models.UserDevice;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDeviceRepository extends MongoRepository<UserDevice, ObjectId> {

    List<UserDevice> findAllByUser(User user);
    Optional<UserDevice> findByUserAndDevice(User user, Device device);
    void deleteByUserAndDevice(User user, Device device);

}
