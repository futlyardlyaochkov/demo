package ru.mtuci.demo.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "license")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class License {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "pub_key")
    private String pub_key;

    @Column(name = "key")
    private String key;
}
