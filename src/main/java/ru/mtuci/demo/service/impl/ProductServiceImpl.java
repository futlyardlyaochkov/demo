package ru.mtuci.demo.service.impl;

import ru.mtuci.demo.models.Product;
import ru.mtuci.demo.repository.ProductRepo;
import ru.mtuci.demo.requests.DataProductRequest;
import ru.mtuci.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepo productRepo;

    @Override
    public Optional<Product> getById(Long id) {
        return productRepo.findById(id);
    }

    private Product edit(Product product, DataProductRequest request) {
        product.setName(request.getName());
        product.setBlocked(request.isBlocked());
        return product;
    }

    @Override
    public Product save(Product request) {
        Product product = new Product();
        product.setId(request.getId());
        product.setName(request.getName());
        product.setBlocked(request.isBlocked());

        return productRepo.save(product);
    }


    @Override
    public List<Product> getAll() {
        return productRepo.findAll();
    }

    @Override
    public Product update(DataProductRequest request) {

        Product product = productRepo.findById(request.getId()).orElseThrow(
                () -> new RuntimeException("Продукт не найден")
        );


        product.setName(request.getName());
        product.setBlocked(request.isBlocked());


        return productRepo.save(product);
    }


    @Override
    public void delete(Long id) {
        productRepo.deleteById(id);  // Прямо передаем id
    }
}
