package ru.mtuci.demo.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Permissions {
    READ("read"),
    MODIFICATION("modification");

    private final String permission;

    Permissions(String permission) {
        this.permission = permission;
    }
}