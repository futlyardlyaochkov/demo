package ru.mtuci.demo.controller;

import ru.mtuci.demo.models.Device;
import ru.mtuci.demo.requests.DataDeviceRequest;
import ru.mtuci.demo.service.impl.DeviceServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/settings/device")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class DeviceController {

    private final DeviceServiceImpl deviceService;

    //сохранение нового устройства
    @PostMapping
    public ResponseEntity<?> save(@RequestParam("user_id") Long userId,
                                  @RequestParam("name") String name,
                                  @RequestParam("mac_address") String macAddress) {
        try {
            // Создаем DataDeviceRequest
            DataDeviceRequest deviceRequest = new DataDeviceRequest(null, userId, name, macAddress);
            // Вызываем метод save
            Device device = deviceService.save(deviceRequest);
            return ResponseEntity.ok(deviceRequest);
        } catch (Exception e) {
            return handleError(e);
        }
    }



    //список всех устройств
    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<Device> devices = deviceService.getAll();
            List<DataDeviceRequest> dataDevices = devices.stream()
                    .map(device -> new DataDeviceRequest(
                            device.getId(),
                            device.getUser().getId(),
                            device.getName(),
                            device.getMacAddress()
                    ))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dataDevices);
        } catch (Exception e) {
            return handleError(e);
        }
    }

    //обновление данных об устройстве
    @PutMapping
    public ResponseEntity<?> update(@RequestParam("id") Long id,
                                    @RequestParam("user_id") Long userId,
                                    @RequestParam("name") String name,
                                    @RequestParam("mac_address") String macAddress) {
        try {
            // Проверка на null или пустое значение mac_address
            if (macAddress == null || macAddress.isEmpty()) {
                // Возвращаем ошибку с конкретным сообщением
                throw new IllegalArgumentException("Параметр 'mac_address' обязателен");
            }

            // Создаем объект DataDeviceRequest для обновления устройства
            DataDeviceRequest deviceRequest = new DataDeviceRequest();
            deviceRequest.setId(id);
            deviceRequest.setUserId(userId);
            deviceRequest.setName(name);
            deviceRequest.setMacAddress(macAddress);

            // Обновляем устройство через сервис
            deviceService.update(deviceRequest);

            // Возвращаем ответ с обновленным устройством
            return ResponseEntity.ok(deviceRequest);
        } catch (Exception e) {
            // Передаем исключение в метод handleError
            return handleError(e);
        }
    }


    //удаление устройства
    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam("id") Long id) {
        try {
            // уадление устройства через сервис
            deviceService.delete(id);
            // успешный ответ
            return ResponseEntity.ok("Устройство удалено");
        } catch (Exception e) {
            // обработка ошибки и возвращение ответа с сообщением об ошибке
            return ResponseEntity.internalServerError().body("Ошибка при удалении устройства: " + e.getMessage());
        }
    }


    //метод для обработки ошибок
    private ResponseEntity<?> handleError(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка: " + e.getMessage());
    }

}
