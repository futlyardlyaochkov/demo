package ru.mtuci.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.mtuci.demo.configuration.JwtTokenProvider;
import ru.mtuci.demo.models.User;
import ru.mtuci.demo.models.AuthenticationResponse;
import ru.mtuci.demo.repository.UserRepo;

@RestController
@RequestMapping("/authentication/signin")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserRepo userRepo;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    //аутентификация пользователя
    @PostMapping
    public ResponseEntity<?> login(
            @RequestParam String login,
            @RequestParam String password) {
        try {
            // аутентификация с помощью AuthenticationManager
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login, password)
            );

            // берем пользователя из бд
            User user = userRepo.findByLogin(login)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            // генерируем для него токен
            String token = jwtTokenProvider.createToken(login, user.getRole().getGrantedAuthorities());

            // возвращаем токен и логин
            return ResponseEntity.ok(new AuthenticationResponse(token, login));

        } catch (AuthenticationException e) {
            // обработка ошибки аутентификации
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid login or password");
        }
    }
}
