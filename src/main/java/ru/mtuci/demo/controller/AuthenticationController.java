package ru.mtuci.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mtuci.demo.configuration.JwtTokenProvider;
import ru.mtuci.demo.models.User;
import ru.mtuci.demo.models.AuthenticationRequest;
import ru.mtuci.demo.models.AuthenticationResponse;
import ru.mtuci.demo.repository.UserRepo;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserRepo userRepository = null;
    private final AuthenticationManager authenticationManager = null;
    private final JwtTokenProvider jwtTokenProvider = null;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {
        try {
            String login = request.getLogin();

            User user = userRepository.findByLogin(login)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    login, request.getPassword())
                    );

            String token = jwtTokenProvider
                    .createToken(login, user.getRole().getGrantedAuthorities());

            return ResponseEntity.ok(new AuthenticationResponse(login, token));
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid login or password");
        }
    }
}
