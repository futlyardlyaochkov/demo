package ru.mtuci.demo.service.impl;

import ru.mtuci.demo.models.LicenseType;
import ru.mtuci.demo.repository.LicenseTypeRepo;
import ru.mtuci.demo.requests.DataLicenseTypeRequest;
import ru.mtuci.demo.service.LicenseTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LicenseTypeServiceImpl implements LicenseTypeService {
    private final LicenseTypeRepo licenseTypeRepo;

    @Override
    public Optional<LicenseType> getById(Long id) {
        return licenseTypeRepo.findById(id);
    }

    @Override
    @Transactional
    public LicenseType save(DataLicenseTypeRequest request) {
        // Создание нового типа лицензии
        LicenseType licenseType = new LicenseType();
        return licenseTypeRepo.save(edit(licenseType, request));
    }

    @Override
    public List<LicenseType> getAll() {
        return licenseTypeRepo.findAll();
    }

    @Override
    @Transactional
    public LicenseType update(DataLicenseTypeRequest request) {
        // Проверка существования типа лицензии
        LicenseType licenseType = licenseTypeRepo.findById(request.getId())
                .orElseThrow(() -> new IllegalArgumentException("Тип лицензии с таким ID не найден"));
        return licenseTypeRepo.save(edit(licenseType, request));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // Проверка существования типа лицензии перед удалением
        if (!licenseTypeRepo.existsById(id)) {
            throw new IllegalArgumentException("Тип лицензии с таким ID не найден");
        }
        licenseTypeRepo.deleteById(id);
    }

    private LicenseType edit(LicenseType licenseType, DataLicenseTypeRequest request) {
        licenseType.setName(request.getName());
        licenseType.setDescription(request.getDescription());
        licenseType.setDefaultDuration(request.getDefaultDuration());
        return licenseType;
    }
}
