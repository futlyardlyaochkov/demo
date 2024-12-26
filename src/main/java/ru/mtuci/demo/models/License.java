package ru.mtuci.demo.models;


import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "license")
public class License {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // первая активация лицензии
    private Date firstActivationDate;

    // окончание действия лицензии
    private Date endingDate;

    // блокировка лицензии
    private boolean isBlocked;

    private Integer deviceCount;

    // длительность действия лицензии
    private Long Duration;

    // код лицензии
    private String code;

    // описание
    @Column(length = 500)
    private String description;

    // тип
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "type_id")
    private LicenseType licenseType;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "license")
    private List<LicenseHistory> licenseHistories;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "license")
    private List<DeviceLicense> deviceLicenses;
}
