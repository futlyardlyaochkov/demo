package ru.mtuci.demo.models;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // уникальный логин пользователя
    @Column(unique = true, nullable = false)
    private String login;

    // хэш пароля пользователя
    @Setter
    @Column(nullable = false)
    private String passwordHash;

    // уникальный email пользователя
    @Column(unique = true, nullable = false)
    private String email;

    // роль пользователя в системе
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private Role role;

    // лицензии, в которых пользователь является пользователем
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private List<License> userLicenses;

    // лицензии, в которых пользователь является владельцем
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner", cascade = CascadeType.ALL)
    private List<License> ownedLicenses;

    // история лицензий
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private List<LicenseHistory> licenseHistories;

    // устройства зарегистрированнные на пользователя
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
    private List<Device> devices;

}
