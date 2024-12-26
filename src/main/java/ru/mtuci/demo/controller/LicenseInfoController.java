package ru.mtuci.demo.controller;

import ru.mtuci.demo.configuration.JwtTokenProvider;
import ru.mtuci.demo.models.User;
import ru.mtuci.demo.models.Device;
import ru.mtuci.demo.models.License;
import ru.mtuci.demo.models.Ticket;
import ru.mtuci.demo.service.impl.DeviceServiceImpl;
import ru.mtuci.demo.service.impl.LicenseServiceImpl;
import ru.mtuci.demo.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/license/details")
@RequiredArgsConstructor
public class LicenseInfoController {

    private final UserServiceImpl userService;
    private final DeviceServiceImpl deviceService;
    private final JwtTokenProvider jwtTokenProvider;
    private final LicenseServiceImpl licenseService;

    // получение информации о лицензиях для устройства
    @PostMapping
    public ResponseEntity<?> getLicenseInfo(@RequestHeader("Authorization") String auth,
                                            @RequestParam("deviceName") String deviceName,
                                            @RequestParam("deviceMacAddress") String deviceMacAddress) {
        try {
            // извлечение логина из токена
            String login = jwtTokenProvider.getUsername(auth.split(" ")[1]);

            User user = userService.getByLogin(login)
                    .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

            // поиск по имени и MAC-адресу
            Device device = deviceService.findDeviceByInfo(deviceName, deviceMacAddress, user)
                    .orElseThrow(() -> new RuntimeException("Устройство не найдено"));

            // получение активных лицензий для устройства
            List<License> licenses = licenseService.getActiveLicensesForDevice(device, user);

            // билеты для каждой лицензии
            List<Ticket> tickets = licenses.stream()
                    .map(license -> licenseService.generateTicket(license, device, "Информация о лицензии на текущее устройство"))
                    .toList();

            return ResponseEntity.ok(tickets);

        } catch (RuntimeException e) {
            // обработка ошибок и возврат сообщения
            return ResponseEntity.badRequest().body(String.format("Ошибка(%s)", e.getMessage()));
        }
    }
}

