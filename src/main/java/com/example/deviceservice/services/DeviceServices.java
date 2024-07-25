package com.example.deviceservice.services;

import com.example.deviceservice.dtos.AddNewDevice;
import com.example.deviceservice.models.Device;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

public interface DeviceServices {
    List<Device> getAll();

    Optional<Device> getById(ObjectId id);
    void addDevice(AddNewDevice request);

}
