package ru.mtuci.demo.controller;

import ru.mtuci.demo.models.User;
import ru.mtuci.demo.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/signup")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserServiceImpl userService;

    // регистрация нового пользователя
    @PostMapping
    public ResponseEntity<?> registration(
            @RequestParam String login,
            @RequestParam String email,
            @RequestParam String password) {
        try {
            // создание нового пользователя
            User user = new User();
            user.setLogin(login);
            user.setEmail(email);

            // проверка, можно ли сохранить пользователя
            boolean isSaved = userService.saveUser(user, password);

            if (!isSaved) {
                return ResponseEntity.badRequest().body("Пользователь с таким логином или email уже существует!");
            }

            return ResponseEntity.ok("Регистрация прошла успешно!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Ошибка при регистрации: " + e.getMessage());
        }
    }
}
