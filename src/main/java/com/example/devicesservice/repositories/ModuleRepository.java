package com.example.devicesservice.repositories;

import com.example.devicesservice.dtos.modules.ModuleResponse;
import com.example.devicesservice.models.Device;
import com.example.devicesservice.models.Module;
import com.example.devicesservice.models.ModuleType;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModuleRepository extends MongoRepository<Module, ObjectId> {

    List<Module> findAllByDevice(Device device);
    Optional<Module> findByIdAndType(ObjectId id, ModuleType type);
    void deleteAllByDevice(Device device);

}
