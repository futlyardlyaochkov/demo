package ru.mtuci.demo.requests;

import lombok.Data;

@Data
public class LicenseCreateRequest {
    private Long productId;
    private Integer device_count, ownerId, licenseTypeId;
    private Long duration;
}
