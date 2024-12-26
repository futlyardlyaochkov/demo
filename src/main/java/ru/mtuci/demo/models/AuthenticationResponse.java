package ru.mtuci.demo.models;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String login;
    private String token;
}
