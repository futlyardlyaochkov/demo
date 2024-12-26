package ru.mtuci.demo.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataDeviceRequest {

    private Long id;
    private Long userId;
    private String name;
    private String macAddress;
}
