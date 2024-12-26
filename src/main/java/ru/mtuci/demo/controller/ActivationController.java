package ru.mtuci.demo.controller;

import ru.mtuci.demo.models.User;
import ru.mtuci.demo.models.Device;
import ru.mtuci.demo.models.Ticket;
import lombok.RequiredArgsConstructor;
import ru.mtuci.demo.configuration.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mtuci.demo.service.impl.DeviceServiceImpl;
import ru.mtuci.demo.service.impl.LicenseServiceImpl;
import ru.mtuci.demo.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/activation")
@RequiredArgsConstructor
public class ActivationController {

    private final UserServiceImpl userService;
    private final DeviceServiceImpl deviceService;
    private final JwtTokenProvider jwtTokenProvider;
    private final LicenseServiceImpl licenseService;

    @PostMapping
    public ResponseEntity<?> activateLicense(@RequestHeader("Authorization") String authHeader,
                                             @RequestParam String name,
                                             @RequestParam String macAddress,
                                             @RequestParam String activationCode) {
        try {
            // извлечь токен и получить логин пользователя
            String token = extractToken(authHeader);
            String login = jwtTokenProvider.getUsername(token);

            // получить пользователя из сервиса
            User user = userService.getByLogin(login)
                    .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

            // регистрация или обновление устройства
            Device device = deviceService.registerOrUpdateDevice(name, macAddress, user);

            // активировация лицензии
            Ticket ticket = licenseService.activateLicense(activationCode, device, user);

            return ResponseEntity.ok(ticket);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Некорректный заголовок Authorization");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(String.format("Ошибка: %s", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Произошла ошибка при активации лицензии: " + e.getMessage());
        }
    }

    //Извлечение токена
    private String extractToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Некорректный заголовок Authorization");
        }
        return authHeader.substring(7);
    }
}