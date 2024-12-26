package ru.mtuci.demo.repository;

import ru.mtuci.demo.models.User;
import ru.mtuci.demo.models.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface DeviceRepo extends JpaRepository<Device, Long> {
    Optional<Device> findByNameAndMacAddressAndUser(String name, String mac_address, User user);
    Optional<Device> findByMacAddress(String mac_address);
}
