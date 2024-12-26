package ru.mtuci.demo.service.impl;

import ru.mtuci.demo.models.Device;
import ru.mtuci.demo.models.DeviceLicense;
import ru.mtuci.demo.models.License;
import ru.mtuci.demo.repository.DeviceLicenseRepo;
import ru.mtuci.demo.repository.DeviceRepo;
import ru.mtuci.demo.repository.LicenseRepo;
import ru.mtuci.demo.service.DeviceLicenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceLicenseServiceImpl implements DeviceLicenseService {

    private final DeviceLicenseRepo deviceLicenseRepo;
    private final DeviceRepo deviceRepo;
    private final LicenseRepo licenseRepo;

    @Override
    public List<DeviceLicense> getAll() {
        return deviceLicenseRepo.findAll();
    }

    @Override
    public DeviceLicense save(Long id, Long deviceId, Long licenseId, Date activationDate) {
        // Найти устройство
        Device device = deviceRepo.findById(deviceId)
                .orElseThrow(() -> new RuntimeException("Устройство не найдено"));

        // Найти лицензию
        License license = licenseRepo.findById(licenseId)
                .orElseThrow(() -> new RuntimeException("Лицензия не найдена"));

        // Создать связь устройства и лицензии
        DeviceLicense deviceLicense = new DeviceLicense();
        deviceLicense.setDevice(device);
        deviceLicense.setLicense(license);
        deviceLicense.setActivationDate(activationDate);

        return deviceLicenseRepo.save(deviceLicense);
    }

    @Override
    public DeviceLicense update(Long id, Long deviceId, Long licenseId, Date activationDate) {
        // Найти существующую запись
        DeviceLicense deviceLicense = deviceLicenseRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Связь не найдена"));

        // Найти устройство
        Device device = deviceRepo.findById(deviceId)
                .orElseThrow(() -> new RuntimeException("Устройство не найдено"));

        // Найти лицензию
        License license = licenseRepo.findById(licenseId)
                .orElseThrow(() -> new RuntimeException("Лицензия не найдена"));

        // Обновить запись
        deviceLicense.setDevice(device);
        deviceLicense.setLicense(license);
        deviceLicense.setActivationDate(activationDate);

        return deviceLicenseRepo.save(deviceLicense);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // Проверить существование записи
        if (!deviceLicenseRepo.existsById(id)) {
            throw new RuntimeException("Связь не найдена");
        }

        // Удалить запись
        deviceLicenseRepo.deleteById(id);
    }


}
