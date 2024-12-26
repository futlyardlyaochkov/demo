package ru.mtuci.demo.service;

import org.springframework.stereotype.Service;
import ru.mtuci.demo.models.DeviceLicense;

import java.sql.Date;
import java.util.List;


public interface DeviceLicenseService {

    // получение всех
    List<DeviceLicense> getAll();

    DeviceLicense save(Long id, Long deviceId, Long licenseId, Date activationDate);

    // обновление
    DeviceLicense update(Long id, Long deviceId, Long licenseId, Date activationDate);

    // удаление
    void delete(Long id);
}
