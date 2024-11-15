package ru.mtuci.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mtuci.demo.models.User;
import ru.mtuci.demo.repository.UserRepo;
import ru.mtuci.demo.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    UserRepo userRepo;

    @Autowired
    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public void save(User user) {
        userRepo.save(user);
    }

    @Override
    public List<User> findAll() {
        List<User> userList = userRepo.findAll();
        return userList;
    }

    @Override
    public User findById(long id) {
        return userRepo.getOne(id);
    }
}
