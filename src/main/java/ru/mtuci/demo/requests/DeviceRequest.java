package ru.mtuci.demo.requests;

import lombok.Data;

@Data
public class DeviceRequest {
    private String activationCode, name, macAddress;
}
