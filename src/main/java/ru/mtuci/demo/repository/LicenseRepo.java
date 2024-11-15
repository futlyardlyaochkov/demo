package ru.mtuci.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mtuci.demo.models.License;
import java.util.Optional;

@Repository
public interface LicenseRepo extends JpaRepository<License, Long> {
    Optional<License> findByID(long id);
}