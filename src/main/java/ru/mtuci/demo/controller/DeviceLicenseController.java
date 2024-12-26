package ru.mtuci.demo.controller;

import ru.mtuci.demo.models.DeviceLicense;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import ru.mtuci.demo.requests.DataDeviceLicenseRequest;
import ru.mtuci.demo.service.impl.DeviceLicenseServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;
import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/settings/deviceLicense")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class DeviceLicenseController {

    private final DeviceLicenseServiceImpl deviceLicenseService;

    //cохранение новой связи между устройством и лицензией
    @PostMapping
    public ResponseEntity<?> save(@RequestParam("id") Long id,
                                  @RequestParam("deviceId") Long deviceId,
                                  @RequestParam("licenseId") Long licenseId,
                                  @RequestParam("activationDate") String activationDate) {
        try {
            // преобразование строки в дату
            Date date = Date.valueOf(activationDate);
            // создание и сохранение связи устройства и лицензии
            DeviceLicense device = deviceLicenseService.save(
                    id,
                    deviceId,
                    licenseId,
                    date
            );

            // ответ с данными созданной связи
            return ResponseEntity.status(201).body(device); // Статус 201 для успешного создания
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Некорректный формат даты. Используйте формат YYYY-MM-DD: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Ошибка при создании связи устройства и лицензии: " + e.getMessage());
        }
    }




    // обновление существующей связи устройства и лицензии
    @PutMapping
    public ResponseEntity<?> update(@RequestParam("id") Long id,
                                    @RequestParam("deviceId") Long deviceId,
                                    @RequestParam("licenseId") Long licenseId,
                                    @RequestParam("activationDate") String activationDate) {
        try {
            Date date = Date.valueOf(activationDate); // Преобразование строки в дату

            // обновление связи через сервис
            DeviceLicense updatedDeviceLicense = deviceLicenseService.update(
                    id,
                    deviceId,
                    licenseId,
                    date
            );

            // возвращащение ответа с обновленными данными
            return ResponseEntity.ok(updatedDeviceLicense); // Статус 200 для успешного обновления
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Некорректный формат даты. Используйте формат YYYY-MM-DD: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Ошибка при обновлении связи устройства и лицензии: " + e.getMessage());
        }
    }



    // удаление связи и лицензии по  идентификатору

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam Long id) {
        try {
            deviceLicenseService.delete(id);
            return ResponseEntity.ok("Лицензия удалена");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



    // получение всех связей и лицензий
    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<DeviceLicense> deviceLicenses = deviceLicenseService.getAll();
            List<DataDeviceLicenseRequest> dataDevices = deviceLicenses.stream()
                    .map(deviceLicense -> new DataDeviceLicenseRequest(
                            deviceLicense.getId(),
                            deviceLicense.getDevice().getId(),
                            deviceLicense.getLicense().getId(),
                            deviceLicense.getActivationDate()
                    ))
                    .collect(Collectors.toList());

            // успешный ответ со списком связей
            return ResponseEntity.ok(dataDevices); // Статус 200 для успешного запроса
        } catch (Exception e) {
            // обработка ошибки и возвращение статуса ошибки с описанием
            return ResponseEntity.internalServerError().body("Ошибка при получении списка связей устройства и лицензии: " + e.getMessage());
        }
    }


    // метод обработки ошибок.

    private ResponseEntity<?> handleError(String message, Exception e) {
        return ResponseEntity.badRequest().body(message + ": " + e.getMessage()); // Статус 400 для ошибки
    }
}
