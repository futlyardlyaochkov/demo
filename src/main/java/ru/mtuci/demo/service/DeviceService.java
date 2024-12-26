package ru.mtuci.demo.service;

import org.springframework.stereotype.Service;
import ru.mtuci.demo.models.User;
import ru.mtuci.demo.models.Device;
import ru.mtuci.demo.requests.DataDeviceRequest;

import java.util.List;
import java.util.Optional;


public interface DeviceService {
    Device registerOrUpdateDevice(String nameDevice, String macDevice, User user);
    Optional<Device> findDeviceByInfo(String name, String mac_address, User user);
    Optional<Device> findDeviceById(Long id);

    // save
    Device save(DataDeviceRequest deviceRequest);

    // read
    List<Device> getAll();

    // update
    Device update(DataDeviceRequest deviceRequest);

    // delete
    void delete(Long id);
}
