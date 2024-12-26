package ru.mtuci.demo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Product")
public class Product {


    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;

    /**Статус блокировки продукта*/
    private boolean isBlocked;

    /**
     * Список лицензий, связанных с этим продуктом.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL)
    private List<License> licenses;
}