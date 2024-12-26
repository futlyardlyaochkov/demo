package ru.mtuci.demo.models;

import lombok.Data;

import java.time.LocalDate;

//Модель для представления информации о билете лицензии
@Data
public class Ticket {
    private LocalDate nowDate;
    private LocalDate activationDate;
    private LocalDate expirationDate;
    private Long expiration;
    private Long userID;
    private Long deviceID;
    private boolean isBlockedLicense;
    private String digitalSignature;
    private String description;
}
