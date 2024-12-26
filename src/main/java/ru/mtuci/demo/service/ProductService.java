package ru.mtuci.demo.service;

import org.springframework.stereotype.Service;
import ru.mtuci.demo.models.Product;
import ru.mtuci.demo.requests.DataProductRequest;
import java.util.List;
import java.util.Optional;


public interface ProductService {
    Optional<Product> getById(Long id);

    // сохранение
    Product save(Product request);

    // получение всех
    List<Product> getAll();

    // обновление
    Product update(DataProductRequest request);
    // удаление
    void delete(Long id);
}
