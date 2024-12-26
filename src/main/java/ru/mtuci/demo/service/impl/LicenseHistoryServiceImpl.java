package ru.mtuci.demo.service.impl;

import ru.mtuci.demo.models.User;
import ru.mtuci.demo.models.License;
import ru.mtuci.demo.models.LicenseHistory;
import ru.mtuci.demo.repository.LicenseHistoryRepo;
import ru.mtuci.demo.repository.LicenseRepo;
import ru.mtuci.demo.requests.DataLicenseHistoryRequest;
import ru.mtuci.demo.service.LicenseHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LicenseHistoryServiceImpl implements LicenseHistoryService {
    private final LicenseHistoryRepo licenseHistoryRepo;
    private final LicenseRepo licenseRepo;
    private final UserServiceImpl userService;

    @Override
    public boolean recordLicenseChange(License license, User owner, String status, String description) {
        LicenseHistory licenseHistory = new LicenseHistory();
        licenseHistory.setLicense(license);
        licenseHistory.setChangeDate(new Date(System.currentTimeMillis()));
        licenseHistory.setUser(owner);
        licenseHistory.setStatus(status);
        licenseHistory.setDescription(description);

        licenseHistoryRepo.save(licenseHistory);
        return true;
    }

    @Override
    public Optional<LicenseHistory> getById(Long id) {
        return licenseHistoryRepo.findById(id);
    }

    /**
     * Метод для обновления сущности LicenseHistory на основе данных из DataLicenseHistoryRequest
     * @param licenseHistory История лицензии
     * @param request Данные для обновления
     * @return обновленная сущность LicenseHistory
     */
    private LicenseHistory edit(LicenseHistory licenseHistory, DataLicenseHistoryRequest request) {
        // Присваиваем лицензии
        License license = licenseRepo.findById(request.getLicenseId()).orElseThrow(
                () -> new RuntimeException("Лицензия не найдена")
        );
        licenseHistory.setLicense(license);

        // Присваиваем пользователю
        User user = userService.getById(request.getUserId()).orElseThrow(
                () -> new RuntimeException("Пользователь не найден")
        );
        licenseHistory.setUser(user);

        // Присваиваем остальные данные
        licenseHistory.setStatus(request.getStatus());
        licenseHistory.setDescription(request.getDescription());
        licenseHistory.setChangeDate(new Date(System.currentTimeMillis()));  // Устанавливаем текущую дату изменения

        return licenseHistory;
    }

    /**
     * Сохранение истории лицензии
     * @param request Запрос с данными для сохранения
     * @return Сохраненная запись истории лицензии
     */
    @Override
    public LicenseHistory save(DataLicenseHistoryRequest request) {
        // Создаем новый объект LicenseHistory, заполняем его и сохраняем
        LicenseHistory licenseHistory = new LicenseHistory();
        return licenseHistoryRepo.save(edit(licenseHistory, request));
    }

    /**
     * Получение всех записей истории лицензий
     * @return Список всех историй лицензий
     */
    @Override
    public List<LicenseHistory> getAll() {
        return licenseHistoryRepo.findAll();
    }

    /**
     * Обновление записи истории лицензии
     * @param request Запрос с данными для обновления
     * @return Обновленная запись истории лицензии
     */
    @Override
    public LicenseHistory update(DataLicenseHistoryRequest request) {
        // Извлекаем запись истории лицензии по ID
        LicenseHistory licenseHistory = licenseHistoryRepo.findById(request.getLicenseId()).orElseThrow(
                () -> new RuntimeException("История лицензии не найдена")
        );

        // Обновляем сущность
        return licenseHistoryRepo.save(edit(licenseHistory, request));
    }

    /**
     * Удаление записи истории лицензии
     * @param id Идентификатор записи для удаления
     */
    @Override
    public void delete(Long id) {
        licenseHistoryRepo.deleteById(id);
    }
}
