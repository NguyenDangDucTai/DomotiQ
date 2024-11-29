package com.example.devicesservice.repositories;

import com.example.devicesservice.models.GestureCameraModule;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GestureCameraModuleRepository extends MongoRepository<GestureCameraModule, ObjectId> {
}
