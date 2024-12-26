package ru.mtuci.demo.requests;

import lombok.Data;

@Data
public class DeviceInfoRequest {
    private String name, macAddress;
}
