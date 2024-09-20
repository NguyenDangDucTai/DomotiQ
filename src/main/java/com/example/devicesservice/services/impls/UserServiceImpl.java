package com.example.devicesservice.services.impls;

import com.example.devicesservice.dtos.DeleteDeviceFromUser;
import com.example.devicesservice.dtos.FindDeviceByRoomName;
import com.example.devicesservice.dtos.RenameDevice;
import com.example.devicesservice.models.DevicesOwend;
import com.example.devicesservice.models.User;
import com.example.devicesservice.repositories.DeviceRepository;
import com.example.devicesservice.repositories.UserRepository;
import com.example.devicesservice.services.UserServices;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
@AllArgsConstructor
public class UserServiceImpl implements UserServices {

    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(ObjectId id) {
        return userRepository.findById(id);
    }

    @Override
    public void addDeviceToUser(ObjectId userId, DevicesOwend request) {
        try {
            Optional<User> userOptional = userRepository.findById(userId);
            if(userOptional.isPresent()){
                User userToUpdate = userOptional.get();
                if(userToUpdate.getDevices() == null){
                    userToUpdate.setDevices(new ArrayList<>());
                }
                userToUpdate.getDevices().add(request);
                userRepository.save(userToUpdate);

            }
            else {

            }
        } catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public void deleteDevicefromUser(ObjectId userId, DeleteDeviceFromUser request) {
        try {
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isPresent()){
                User userToUpdate = userOptional.get();
                List<DevicesOwend> listDeviceOwend = userToUpdate.getDevices();

                String deviceIdToDelete = request.getDeviceId();
                DevicesOwend deviceToDelete = null;
                for (DevicesOwend device : listDeviceOwend) {
                    if (device.getDeviceId().equals(deviceIdToDelete)) {
                        deviceToDelete = device;
                        break;
                    }
                }
                if (deviceToDelete != null) {
                    listDeviceOwend.remove(deviceToDelete);
                    userToUpdate.setDevices(listDeviceOwend);
                    userRepository.save(userToUpdate);
                } else {
                    throw new RuntimeException("Device not found for deletion");
                }
            }
            else {
                throw new RuntimeException("User not found with ID: " + userId);
            }
        } catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public List<DevicesOwend> findDeviceByRoomName(ObjectId userId, FindDeviceByRoomName request) {
        try{
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isPresent()){
                User userToUpdate = userOptional.get();
                List<DevicesOwend> listDeviceOwend = userToUpdate.getDevices();

                String roomNameToFind = request.getRoomName();
                List<DevicesOwend> devicesInRoom = new ArrayList<>();

                for (DevicesOwend device : listDeviceOwend) {
                    if (device.getRoomName().equals(roomNameToFind)) {
                        devicesInRoom.add(device);
                    }
                }

                return devicesInRoom;

            }
            else {
                throw new RuntimeException("Device not found for deletion");
            }
        }
        catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public void renameDevice(ObjectId userId, RenameDevice request) {
        try{
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isPresent()) {
                User userToUpdate = userOptional.get();
                List<DevicesOwend> devicesOwned = userToUpdate.getDevices();
                String deviceIdToRename = request.getDeviceId();
                String newDisplayName = request.getDisplayName();

                for (DevicesOwend device : devicesOwned) {
                    if (device.getDeviceId().equals(deviceIdToRename)) {
                        device.setDisplayName(newDisplayName);
                        break;
                    }
                }
                userToUpdate.setDevices(devicesOwned);
                userRepository.save(userToUpdate);
            }
            else {
                throw new RuntimeException("Device not found for deletion");
            }
        }
        catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }


}
