package com.example.devicesservice.repositories;

import com.example.devicesservice.models.Module;
import com.example.devicesservice.models.ModuleLog;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleLogRepository  extends MongoRepository<ModuleLog, ObjectId> {

    List<ModuleLog> findAllByModule(Module module);

}
