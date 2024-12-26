package ru.mtuci.demo.service;


import org.springframework.stereotype.Service;
import ru.mtuci.demo.models.User;


public interface AuthenticationService {
    boolean authenticate(User user, String password);
}
