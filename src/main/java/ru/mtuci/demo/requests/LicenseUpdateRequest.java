package ru.mtuci.demo.requests;

import lombok.Data;

@Data
public class LicenseUpdateRequest {
    private String password, codeActivation;
}
