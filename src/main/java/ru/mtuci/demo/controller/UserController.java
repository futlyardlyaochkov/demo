package ru.mtuci.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import ru.mtuci.demo.requests.DataUserRequest;
import ru.mtuci.demo.service.UserService;
import ru.mtuci.demo.service.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.*;
import ru.mtuci.demo.models.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/settings/user")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    private final UserServiceImpl userService;

    // создание нового пользователя
    @PostMapping
    public ResponseEntity<?> save(@RequestParam String login,
                                  @RequestParam String password,
                                  @RequestParam String email,
                                  @RequestParam String role) {
        try {
            // Создание нового объекта , заполняя его значениями из параметров запроса
            DataUserRequest request = new DataUserRequest(null, login, password, email, role);
            User user = userService.save(request);
            request.setId(user.getId());
            return ResponseEntity.status(201).body(request);
        } catch (Exception e) {
            return handleError("Ошибка при создании пользователя", e);
        }
    }

    // получение всех пользователей
    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<User> users = userService.getAll();
            List<DataUserRequest> data = users.stream()
                    .map(user -> new DataUserRequest(
                            user.getId(),
                            user.getLogin(),
                            user.getPasswordHash(),
                            user.getEmail(),
                            user.getRole().name()
                    ))
                    .toList();
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            return handleError("Ошибка при получении пользователей", e);
        }
    }

    // обновление данных пользователя
    @PutMapping
    public ResponseEntity<?> update(@RequestParam Long id,
                                    @RequestParam String login,
                                    @RequestParam String password,
                                    @RequestParam String email,
                                    @RequestParam String role) {
        try {
            // Создаем новый объект DataUserRequest с данными для обновления
            DataUserRequest request = new DataUserRequest(id, login, password, email, role);
            userService.update(request);
            return ResponseEntity.ok(request);
        } catch (Exception e) {
            return handleError("Ошибка при обновлении пользователя", e);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam Long id) {
        try {
            userService.delete(id);
            return ResponseEntity.ok("Пользователь удалён");
        } catch (Exception e) {
            return handleError("Ошибка при удалении пользователя", e);
        }
    }

    private ResponseEntity<?> handleError(String message, Exception e) {
        return ResponseEntity.status(400).body(message + ": " + e.getMessage());
    }
}