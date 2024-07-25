package com.example.deviceservice.repositories;

import com.example.deviceservice.models.Device;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DeviceRepository extends MongoRepository<Device, ObjectId> {


}
