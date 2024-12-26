package ru.mtuci.demo.repository;

import ru.mtuci.demo.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Long> {
}