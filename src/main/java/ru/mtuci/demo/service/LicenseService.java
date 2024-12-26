package ru.mtuci.demo.service;

import org.springframework.stereotype.Service;
import ru.mtuci.demo.models.Device;
import ru.mtuci.demo.models.License;
import ru.mtuci.demo.models.Ticket;
import ru.mtuci.demo.models.User;
import ru.mtuci.demo.requests.DataLicenseRequest;

import java.util.List;


public interface LicenseService {
    License createLicense(
            Long productId,
            Long ownerId,
            Long licenseTypeId,
            Integer deviceCount,
            Long duration,
            String firstActivationDate,
            String endingDate,
            boolean isBlocked,
            String code,
            String description
    );



    Ticket activateLicense(String activationCode, Device device, User user);
    Ticket generateTicket(License license, Device device, String description);
    List<Ticket> licenseRenewal(String activationCode, User user);

    boolean validateLicense(License license, Device device, User user);

    void updateLicense(License license);

    List<License> getActiveLicensesForDevice(Device device, User user);

    // сохранение
    License save(DataLicenseRequest request);

    // получение всех
    List<License> getAll();

    // обновление
    License update(DataLicenseRequest request);

    // удаление
    void delete(Long id);
}
