package ru.mtuci.demo.models;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Date;

// модель, которая связывает устройство с лицензией.
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "device_license")
public class DeviceLicense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "activation_date", nullable = false)
    private Date activationDate;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "license_id", nullable = false)
    private License license;

}
