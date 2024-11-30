package ru.mtuci.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationResponse {
    private String login;
    private String token;

    public AuthenticationResponse(String login, String token) {
    }
}
