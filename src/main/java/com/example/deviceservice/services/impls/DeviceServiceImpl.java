package com.example.deviceservice.services.impls;

import com.example.deviceservice.dtos.AddNewDevice;
import com.example.deviceservice.models.Device;
import com.example.deviceservice.repositories.DeviceRepository;
import com.example.deviceservice.services.DeviceServices;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class DeviceServiceImpl implements DeviceServices {

    private final DeviceRepository deviceRepository;


    @Override
    public List<Device> getAll() {
        return deviceRepository.findAll();
    }

    @Override
    public Optional<Device> getById(ObjectId id) {
        return deviceRepository.findById(id);
    }

    @Override
    public void addDevice(AddNewDevice request) {
        try{
            Device deviceToAdd = new Device();
            deviceToAdd.setName(request.getName());
            deviceToAdd.setProductId(request.getProductId());

            deviceRepository.save(deviceToAdd);
        }
        catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

}
