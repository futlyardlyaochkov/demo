package ru.mtuci.demo.service;

import org.springframework.stereotype.Service;
import ru.mtuci.demo.models.LicenseType;
import ru.mtuci.demo.requests.DataLicenseTypeRequest;

import java.util.List;
import java.util.Optional;


public interface LicenseTypeService {
    Optional<LicenseType> getById(Long id);

    // save
    LicenseType save(DataLicenseTypeRequest request);

    // read
    List<LicenseType> getAll();

    // update
    LicenseType update(DataLicenseTypeRequest request);

    // удаление
    void delete(Long id);
}
