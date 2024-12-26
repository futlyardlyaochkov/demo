package ru.mtuci.demo.controller;

import ru.mtuci.demo.models.LicenseType;
import ru.mtuci.demo.requests.DataLicenseTypeRequest;
import ru.mtuci.demo.service.impl.LicenseTypeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/settings/licenseType")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class LicenseTypeController {

    private final LicenseTypeServiceImpl licenseTypeService;

    // cохранение нового типа лицензии
    @PostMapping
    public ResponseEntity<?> save(@RequestParam(required = false) Long id,
                                  @RequestParam("name") String name,
                                  @RequestParam("description") String description,
                                  @RequestParam("defaultDuration") Long defaultDuration) {
        try {
            // проверяем на пустые параметры
            if (name == null || name.isEmpty() || description == null || description.isEmpty() || defaultDuration == null) {
                return ResponseEntity.badRequest().body("Параметры не могут быть пустыми");
            }

            DataLicenseTypeRequest request = new DataLicenseTypeRequest(id, name, description, defaultDuration);

            if (id == null) {
                // если id отсутствует, создаем новый тип лицензии
                LicenseType license = licenseTypeService.save(request);
                request.setId(license.getId());
                return ResponseEntity.status(HttpStatus.CREATED).body(request);
            } else {
                // если id присутствует, обновляем существующий тип лицензии
                licenseTypeService.update(request);
                return ResponseEntity.ok(request);
            }

        } catch (Exception e) {
            return handleError("Ошибка при сохранении типа лицензии", e);
        }
    }

    // список всех типов лицензий
    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            var licenseTypes = licenseTypeService.getAll();
            return ResponseEntity.ok(licenseTypes); // Код 200 для успешного запроса
        } catch (Exception e) {
            return handleError("Ошибка при получении списка типов лицензий", e);
        }
    }

    // обновление типа лицензии
    @PutMapping
    public ResponseEntity<?> update(@RequestParam("id") Long id,
                                    @RequestParam("name") String name,
                                    @RequestParam("description") String description,
                                    @RequestParam("defaultDuration") Long defaultDuration) {
        try {
            // проверка на наличие параметров
            if (name == null || name.isEmpty() || description == null || description.isEmpty() || defaultDuration == null) {
                return ResponseEntity.badRequest().body("Все поля обязательны для заполнения");
            }

            // объект запроса с новыми данными
            DataLicenseTypeRequest request = new DataLicenseTypeRequest(id, name, description, defaultDuration);

            // обновление типа лицензии через сервис
            licenseTypeService.update(request);

            return ResponseEntity.ok("Тип лицензии успешно обновлен");

        } catch (Exception e) {
            return handleError("Ошибка при обновлении типа лицензии", e);
        }
    }


    // удаление типа лицензии
    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam("id") Long id) {
        try {
            licenseTypeService.delete(id);
            return ResponseEntity.ok("Тип лицензии удалён");
        } catch (Exception e) {
            return handleError("Ошибка при удалении типа лицензии", e);
        }
    }

    // метод для обработки ошибок
    private ResponseEntity<?> handleError(String message, Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message + ": " + e.getMessage());
    }
}
