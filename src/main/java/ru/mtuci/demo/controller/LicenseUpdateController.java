package ru.mtuci.demo.controller;

import ru.mtuci.demo.configuration.JwtTokenProvider;
import ru.mtuci.demo.models.User;
import ru.mtuci.demo.models.Ticket;
import ru.mtuci.demo.requests.LicenseUpdateRequest;
import ru.mtuci.demo.service.impl.AuthenticationServiceImpl;
import ru.mtuci.demo.service.impl.LicenseServiceImpl;
import ru.mtuci.demo.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/license/renew")
@RequiredArgsConstructor
public class LicenseUpdateController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserServiceImpl userService;
    private final AuthenticationServiceImpl authenticationService;
    private final LicenseServiceImpl licenseService;

    // обновление лицензии пользователя
    @PostMapping
    public ResponseEntity<?> licenseUpdate(@RequestHeader("Authorization") String auth, @RequestParam LicenseUpdateRequest licenseUpdateRequest) {
        try {
            String token = auth.split(" ")[1];
            String login = jwtTokenProvider.getUsername(token);

            User user = userService.getByLogin(login)
                    .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

            // аутентификация пользователя
            if (!authenticationService.authenticate(user, licenseUpdateRequest.getPassword())) {
                throw new RuntimeException("Аутентификация не удалась");
            }

            // обработка продления лицензии
            List<Ticket> tickets = licenseService.licenseRenewal(licenseUpdateRequest.getCodeActivation(), user);

            // обновленные билеты
            return ResponseEntity.ok(tickets);
        } catch (RuntimeException e) {
            // ошибка с сообщением
            return ResponseEntity.badRequest().body(String.format("Ошибка: %s", e.getMessage()));
        }
    }
}
