package ru.mtuci.demo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mtuci.demo.configuration.SecurityConf;
import ru.mtuci.demo.models.Role;
import ru.mtuci.demo.models.User;
import ru.mtuci.demo.repository.UserRepo;
import ru.mtuci.demo.requests.DataUserRequest;
import ru.mtuci.demo.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final SecurityConf securityConfig;

    @Override
    public Optional<User> getById(Long id) {
        return userRepo.findById(id);
    }

    @Override
    public Optional<User> getByLogin(String login) {
        return userRepo.findByLogin(login);
    }

    private User edit(User user, DataUserRequest request) {
        user.setLogin(request.getLogin());
        user.setPasswordHash(securityConfig.passwordEncoder().encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRole(Role.valueOf(request.getRole()));
        return user;
    }


    @Override
    public User save(DataUserRequest request) {
        return userRepo.save(edit(new User(), request));
    }

    @Override
    public List<User> getAll() {
        return userRepo.findAll();
    }

    @Override
    public User update(DataUserRequest request) {
        User user = getById(request.getId()).orElseThrow(
                () -> new RuntimeException("Пользователь не найден")
        );
        return userRepo.save(edit(user, request));
    }


    @Override
    public void delete(Long id) {
        userRepo.deleteById(id);
    }

    public boolean saveUser(User user, String password) {
        Optional<User> userFromDB = userRepo.findByLogin(user.getLogin());

        if (userFromDB.isPresent()) return false;
        user.setRole(Role.USER);

        user.setPasswordHash(securityConfig.passwordEncoder().encode(password));
        user.setEmail(user.getEmail());
        user.setLogin(user.getLogin());

        userRepo.save(user);
        return true;
    }
}
