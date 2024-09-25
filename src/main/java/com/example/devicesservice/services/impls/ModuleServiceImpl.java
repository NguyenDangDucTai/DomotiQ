package com.example.devicesservice.services.impls;

import com.example.devicesservice.exceptions.NotFoundException;
import com.example.devicesservice.models.Module;
import com.example.devicesservice.models.ModuleType;
import com.example.devicesservice.repositories.ModuleRepository;
import com.example.devicesservice.services.ModuleService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRepository;

    @Override
    public Module findModuleById(String id) {
        return moduleRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new NotFoundException(String.format("Module with id %s not found", id)));
    }

    @Override
    public Module findModuleById(String id, ModuleType type) {
        return moduleRepository.findByIdAndType(new ObjectId(id), type)
                .orElseThrow(() -> new NotFoundException(String.format("Module with id %s not found", id)));
    }

}
