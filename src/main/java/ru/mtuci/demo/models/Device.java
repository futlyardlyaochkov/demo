package ru.mtuci.demo.models;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

// Модель устройства, связанного с пользователем и лицензиями
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Device")
public class Device {

    // уникальный идентификатор
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String macAddress;

    // пользователь, которому принадлежит устройство
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // лицензии
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "device", cascade = CascadeType.ALL)
    private List<DeviceLicense> deviceLicenses;
}
