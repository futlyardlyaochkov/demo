package ru.mtuci.demo.service.impl;

import ru.mtuci.demo.models.User;
import ru.mtuci.demo.models.Device;
import ru.mtuci.demo.repository.DeviceRepo;
import ru.mtuci.demo.requests.DataDeviceRequest;
import ru.mtuci.demo.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {
    private final DeviceRepo deviceRepo;
    private final UserServiceImpl userServiceImpl;

    private Device createDevice(String nameDevice, String macDevice, User user) {
        Device device = new Device();
        device.setName(nameDevice);
        device.setMacAddress(macDevice);
        device.setUser(user);
        return deviceRepo.save(device);
    }

    @Override
    public Device registerOrUpdateDevice(String nameDevice, String macDevice, User user) {
        Device device = deviceRepo.findByMacAddress(macDevice).orElse(null);

        // новое устройство у пользователя
        if (device == null || !device.getUser().getId().equals(user.getId())) {
            device = new Device();
            device.setName(nameDevice);
            device.setMacAddress(macDevice);
            device.setUser(user);
        }
        else if (!nameDevice.equals(device.getName())) {
            device.setName(nameDevice);
        }

        return deviceRepo.save(device);
    }

    @Override
    public Optional<Device> findDeviceByInfo(String name, String mac_address, User user) {
        return deviceRepo.findByNameAndMacAddressAndUser(name, mac_address, user);
    }

    @Override
    public Optional<Device> findDeviceById(Long id) {
        return deviceRepo.findById(id);
    }

    private Device edit(Device device, DataDeviceRequest deviceRequest) {
        device.setName(deviceRequest.getName());
        device.setMacAddress(deviceRequest.getMacAddress());
        device.setUser(userServiceImpl.getById(deviceRequest.getUserId()).orElseThrow(
                () -> new RuntimeException("Пользователь не найден")
        ));
        return device;
    }

    @Override
    public Device save(DataDeviceRequest request) {
        Device device = new Device();
        device.setName(request.getName());
        device.setMacAddress(request.getMacAddress());

        // Назначаем пользователя
        User user = userServiceImpl.getById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        device.setUser(user);

        return deviceRepo.save(device);
    }


    @Override
    public List<Device> getAll() {
        return deviceRepo.findAll();
    }

    @Override
    public Device update(DataDeviceRequest deviceRequest) {
        Device device = deviceRepo.findById(deviceRequest.getId()).orElseThrow(
                () -> new RuntimeException("Устройство не найдено")
        );
        return deviceRepo.save(edit(device, deviceRequest));
    }

    @Override
    public void delete(Long id) {
        deviceRepo.deleteById(id);
    }
}
