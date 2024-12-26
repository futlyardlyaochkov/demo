package ru.mtuci.demo.repository;

import ru.mtuci.demo.models.LicenseType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LicenseTypeRepo extends JpaRepository<LicenseType, Long> {
}
