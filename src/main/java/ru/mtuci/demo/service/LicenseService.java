package ru.mtuci.demo.service;

import ru.mtuci.demo.models.License;

import java.util.List;

public interface LicenseService {
    void save(License license);

    List<License> findAll();

    License findById(long id);
}
