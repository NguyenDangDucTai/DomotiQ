package com.example.devicesservice.repositories;

import com.example.devicesservice.models.Device;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface DeviceRepository extends MongoRepository<Device, ObjectId> {


}
