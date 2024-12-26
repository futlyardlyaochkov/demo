package ru.mtuci.demo.service;

import org.springframework.stereotype.Service;
import ru.mtuci.demo.models.User;
import ru.mtuci.demo.models.License;
import ru.mtuci.demo.models.LicenseHistory;
import ru.mtuci.demo.requests.DataLicenseHistoryRequest;

import java.util.List;
import java.util.Optional;


public interface LicenseHistoryService {
    boolean recordLicenseChange(
            License license, User owner,
            String status, String description);
    Optional<LicenseHistory> getById(Long id);

    // сохранение
    LicenseHistory save(DataLicenseHistoryRequest request);

    // получение всех
    List<LicenseHistory> getAll();

    // обновление
    LicenseHistory update(DataLicenseHistoryRequest request);

    // удаление
    void delete(Long id);
}
