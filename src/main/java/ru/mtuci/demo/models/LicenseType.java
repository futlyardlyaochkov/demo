package ru.mtuci.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

// модель типа лицензии
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "LicenseType")
public class LicenseType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(length = 500)
    private String description;
    private Long defaultDuration;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "licenseType")
    @JsonIgnore  // Игнорируем поле при сериализации
    private List<License> licenses;
}
