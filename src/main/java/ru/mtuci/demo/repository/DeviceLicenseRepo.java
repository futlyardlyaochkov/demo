package ru.mtuci.demo.repository;

import ru.mtuci.demo.models.Device;
import ru.mtuci.demo.models.License;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.mtuci.demo.models.DeviceLicense;
import java.util.Optional;

public interface DeviceLicenseRepo extends JpaRepository<DeviceLicense, Long> {

    // Метод для поиска записи DeviceLicense
    Optional<DeviceLicense> findByDeviceAndLicense(Device device, License license);
}
