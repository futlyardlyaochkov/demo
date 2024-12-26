package ru.mtuci.demo.repository;

import ru.mtuci.demo.models.LicenseHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LicenseHistoryRepo extends JpaRepository<LicenseHistory, Long> {
}
