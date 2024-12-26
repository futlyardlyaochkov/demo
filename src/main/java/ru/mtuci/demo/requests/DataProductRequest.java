package ru.mtuci.demo.requests;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataProductRequest {
    private Long id;           // Идентификатор продукта
    private String name;       // Название продукта
    private boolean isBlocked;   // Статус блокировки
}
