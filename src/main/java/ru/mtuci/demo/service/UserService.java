package ru.mtuci.demo.service;

import ru.mtuci.demo.models.User;

import java.util.List;

public interface UserService {
    void save(User user);

    List<User> findAll();

    User findById(long id);
}
