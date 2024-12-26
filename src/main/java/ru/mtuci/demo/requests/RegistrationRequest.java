package ru.mtuci.demo.requests;

import lombok.Data;

@Data
public class RegistrationRequest {
    private String login, email, password;
}
