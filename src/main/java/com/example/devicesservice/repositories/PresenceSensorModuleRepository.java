package com.example.devicesservice.repositories;

import com.example.devicesservice.models.PresenceSensorModule;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PresenceSensorModuleRepository extends MongoRepository<PresenceSensorModule, ObjectId> {

}
