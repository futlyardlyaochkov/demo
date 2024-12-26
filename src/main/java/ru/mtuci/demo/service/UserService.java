package ru.mtuci.demo.service;

import org.springframework.stereotype.Service;
import ru.mtuci.demo.models.User;
import ru.mtuci.demo.requests.DataUserRequest;

import java.util.List;
import java.util.Optional;


public interface UserService {
    Optional<User> getById(Long id);
    Optional<User> getByLogin(String login);

    User save(DataUserRequest request);

    // получение всех
    List<User> getAll();

    // обновление
    User update(DataUserRequest request);

    // удаление
    void delete(Long id);
}
