package com.example.devicesservice.services;

import com.example.devicesservice.dtos.AddDeviceToUser;
import com.example.devicesservice.dtos.DeleteDeviceFromUser;
import com.example.devicesservice.dtos.FindDeviceByRoomName;
import com.example.devicesservice.dtos.RenameDevice;
import com.example.devicesservice.models.DevicesOwend;
import com.example.devicesservice.models.User;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

public interface UserServices {
    List<User> getAll();

    Optional<User> findById(ObjectId id);

    void addDeviceToUser(ObjectId userId, DevicesOwend request);
    void deleteDevicefromUser(ObjectId userId, DeleteDeviceFromUser request);

    List<DevicesOwend> findDeviceByRoomName(ObjectId userId, FindDeviceByRoomName request);

    void renameDevice(ObjectId userId, RenameDevice request);
}
