package ru.mtuci.demo.requests;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String login, password;
}
