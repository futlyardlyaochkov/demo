package ru.mtuci.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.mtuci.demo.models.License;
import ru.mtuci.demo.service.LicenseService;

import java.util.List;

@RestController
@RequestMapping("/license")
public class LicenseController {
    private final LicenseService licenseService;

    public  LicenseController(LicenseService licenseService) {
        this.licenseService = licenseService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('read')")
    public List<License> findAll() { return licenseService.findAll(); }

    @PostMapping("/savelicense")
    @PreAuthorize("hasAnyAuthority('modification')")
    public void save(@RequestBody License license) { licenseService.save(license); }
}
