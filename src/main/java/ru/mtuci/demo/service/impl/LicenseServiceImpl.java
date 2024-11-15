package ru.mtuci.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mtuci.demo.models.License;
import ru.mtuci.demo.repository.LicenseRepo;
import ru.mtuci.demo.service.LicenseService;

import java.util.List;

@Service
public class LicenseServiceImpl implements LicenseService {
    LicenseRepo licenseRepo;

    @Autowired
    public LicenseServiceImpl(LicenseRepo licenseRepo) {
        this.licenseRepo = licenseRepo;
    }

    @Override
    public void save(License license) {
        licenseRepo.save(license);
    }

    @Override
    public List<License> findAll() {
        List<License> licenseList = licenseRepo.findAll();
        return licenseList;
    }

    @Override
    public License findById(long id) {
        return licenseRepo.getOne(id);
    }
}
