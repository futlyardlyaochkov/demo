package ru.mtuci.demo.controller;

import ru.mtuci.demo.models.Product;
import ru.mtuci.demo.requests.DataProductRequest;
import ru.mtuci.demo.service.impl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/settings/product")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class ProductController {

    private final ProductServiceImpl productService;

    // cохранение нового продукта
    @PostMapping
    public ResponseEntity<?> save(@RequestParam String name,
                                  @RequestParam boolean isBlocked) {
        try {
            // проверка чтобы значение name не было пустым
            if (name == null || name.isEmpty()) {
                return ResponseEntity.badRequest().body("Поле 'name' не может быть пустым");
            }

            Product product = productService.save(new Product());

            return ResponseEntity.status(HttpStatus.CREATED).body(product);
        } catch (Exception e) {
            return handleError("Ошибка при сохранении продукта", e);
        }
    }

    // получение всех продуктов
    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<Product> products = productService.getAll();

            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return handleError("Ошибка при получении списка продуктов", e);
        }
    }

    // обновление данных продукта
    @PutMapping
    public ResponseEntity<?> update(@RequestParam Long id,
                                    @RequestParam String name,
                                    @RequestParam boolean isBlocked) {
        try {
            // проверка значения name
            if (name == null || name.isEmpty()) {
                return ResponseEntity.badRequest().body("Поле 'name' не может быть пустым");
            }

            // создание объекта с данными для обновления
            DataProductRequest request = new DataProductRequest();
            request.setId(id);
            request.setName(name);
            request.setBlocked(isBlocked);

            productService.update(request);

            return ResponseEntity.ok("Продукт успешно обновлен");

        } catch (Exception e) {
            return handleError("Ошибка при обновлении продукта", e);
        }
    }

    // удаление продукта
    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam Long id) {
        try {
            productService.delete(id);
            return ResponseEntity.ok("Продукт удалён");
        } catch (Exception e) {
            return handleError("Ошибка при удалении продукта", e);
        }
    }

    // метод для обработки ошибок
    private ResponseEntity<?> handleError(String message, Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message + ": " + e.getMessage());
    }
}
