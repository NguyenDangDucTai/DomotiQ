package com.example.deviceservice.services;

import com.example.deviceservice.dtos.DeleteDeviceFromUser;
import com.example.deviceservice.dtos.FindDeviceByRoomName;
import com.example.deviceservice.dtos.RenameDevice;
import com.example.deviceservice.models.DevicesOwend;
import com.example.deviceservice.models.User;
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
