package ru.mtuci.demo.controller;

import ru.mtuci.demo.models.License;
import ru.mtuci.demo.requests.DataLicenseRequest;
import ru.mtuci.demo.service.impl.LicenseServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/license/add")
@RequiredArgsConstructor
public class LicenseCreateController {

    private final LicenseServiceImpl licenseService;

    // cоздание новой лицензии
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createLicense(
            @RequestParam("type_id") Long typeId,
            @RequestParam("product_id") Long productId,
            @RequestParam("user_id") Long userId,
            @RequestParam("owner_id") Long ownerId,
            @RequestParam("first_activation_date") String firstActivationDate,
            @RequestParam("ending_date") String endingDate,
            @RequestParam("blocked") boolean blocked,
            @RequestParam("device_count") Integer deviceCount,
            @RequestParam("duration") Long duration,
            @RequestParam("code") String code,
            @RequestParam("description") String description) {

        try {
            // метод  для создания лицензии
            License license = licenseService.createLicense(
                    productId,
                    ownerId,
                    typeId,
                    deviceCount,
                    duration,
                    firstActivationDate,
                    endingDate,
                    blocked,
                    code,
                    description
            );

            // создание ответа с данными лицензии
            DataLicenseRequest dataLicenseRequest = new DataLicenseRequest(
                    license.getId(), // id теперь будет автоматически присвоено
                    license.getLicenseType().getId(),
                    license.getProduct().getId(),
                    license.getUser() != null ? license.getUser().getId() : null,  // Если нужно, добавьте ID пользователя, если он есть
                    license.getOwner().getId(),
                    license.getFirstActivationDate(),
                    license.getEndingDate(),
                    license.isBlocked(),
                    license.getDeviceCount(),
                    license.getDuration(),
                    license.getCode(),
                    license.getDescription()
            );


            return ResponseEntity.ok(dataLicenseRequest);

        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Ошибка в формате числового значения: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Ошибка: " + e.getMessage());
        }
    }
}

