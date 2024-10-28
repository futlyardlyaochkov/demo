package ru.mtuci.demo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue
    private int id;

    @Column(unique = true)
    private String login;
    private String password;

    @OneToOne
    @MapsId
    private License license;
}
