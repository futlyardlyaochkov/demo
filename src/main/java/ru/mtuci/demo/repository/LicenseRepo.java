package ru.mtuci.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import ru.mtuci.demo.models.License;


public interface LicenseRepo extends JpaRepository<License, Long> {
    Optional<License> findByCode(String licenseCode);
}