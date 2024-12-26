package ru.mtuci.demo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.mtuci.demo.models.User;
import ru.mtuci.demo.service.AuthenticationService;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;

    @Override
    public boolean authenticate(User user, String password) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getLogin(), password)
        ).isAuthenticated();
    }
}
