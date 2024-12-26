package ru.mtuci.demo.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LicenseHistoryStatus {

    CREATE("Создана"),
    MODIFICATION("Изменена"),
    ACTIVATE("Активирована"),
    ERROR("Ошибка"),
    EXPIRED("Истекла");
    private final String status;

    //строковое представление статуса
    @Override
    public String toString() {
        return status;
    }
}
