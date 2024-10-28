package ru.mtuci.demo.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    private String key, pub_key, name;
}
