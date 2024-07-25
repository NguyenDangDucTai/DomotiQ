package com.example.deviceservice.services;

import com.example.devicesservice.dtos.AddNewDevice;
import com.example.devicesservice.models.Device;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

public interface DeviceServices {
    List<Device> getAll();

    Optional<Device> getById(ObjectId id);
    void addDevice(AddNewDevice request);

}
