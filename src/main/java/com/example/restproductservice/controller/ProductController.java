package com.example.restproductservice.controller;

import com.example.restproductservice.dto.ProductDTO;
import com.example.restproductservice.dto.ProductFilter;
import com.example.restproductservice.model.Product;
import com.example.restproductservice.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для работы с продуктами. Позволяет создавать новые продукты,
 * удалять и обновлять существующие продукты, а также получать список продуктов по определенным критериям.
 */
@Tag(name = "Product Controller",
        description = "Контроллер служащий для полученеия списка продуктов по определенным критериям и  создания, удаления, обновления продукта.")
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ModelMapper modelMapper;

    /**
     * Создает новый продукт в каталоге.
     *
     * @param productDTO DTO продукта для создания.
     * @return Статус HTTP OK при успешном создании.
     */
    @Operation(summary = "Создание продукта",
            description = "Метод создает продукт с использованием данных постовляемых в теле запроса." +
                    " Возвращает статус успешного создания продукта")
    @PostMapping("/create")
    public ResponseEntity<HttpStatus> createProduct(@RequestBody ProductDTO productDTO) {
        productService.save(convertToProduct(productDTO));
        return ResponseEntity.ok(HttpStatus.OK);

    }

    /**
     * Удаляет продукт из каталога по его идентификатору.
     *
     * @param id Идентификатор продукта для удаления.
     * @return Статус HTTP OK при успешном удалении.
     */
    @Operation(summary = "Удаление продукта по идентификатору",
            description = "Метод удаляет продукт из каталога по указанному идентификатору. Возвращает статус успешного удаления.")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") long id) {
        productService.delete(productService.findById(id).getId());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * Обновляет информацию о существующем продукте.
     *
     * @param id Идентификатор продукта для обновления.
     * @param productDTO Новые данные продукта.
     * @return Статус HTTP OK при успешном обновлении.
     */
    @Operation(summary = "Обновление информации о продукте",
            description = "Метод обновляет информацию о продукте по указанному идентификатору с использованием данных, " +
                    "предоставленных в теле запроса. Возвращает статус успешного обновления.")
    @PatchMapping("/update/{id}")
    public ResponseEntity<HttpStatus> updateProduct(@PathVariable("id") long id, @RequestBody ProductDTO productDTO) {
        productService.update(id, convertToProduct(productDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * Возвращает список продуктов, соответствующих заданным критериям поиска.
     *
     * @param categoryId Идентификатор категории продукта.
     * @param name Наименование продукта.
     * @param priceLow Нижняя граница цены.
     * @param priceHigh Верхняя граница цены.
     * @return Список продуктов или статус NO CONTENT, если ничего не найдено.
     */
    @Operation(summary = "Поиск продуктов по критериям",
            description = "Метод возвращает список продуктов, соответствующих заданным критериям поиска. " +
                    "Поддерживает фильтрацию по категории, наименованию, диапазону цен.")
    @GetMapping("/search")
    public ResponseEntity<?> findByCriteria(
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "priceLow", required = false) Double priceLow,
            @RequestParam(value = "priceHigh", required = false) Double priceHigh) {
        ProductFilter filter = new ProductFilter(categoryId, name, priceLow, priceHigh);
        List<Product> products = productService.findByAllFilter(filter);

        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }

    /**
     * Преобразует ProductDTO в сущность Product.
     *
     * @param productDTO DTO продукта.
     * @return Преобразованная сущность Product.
     */
    private Product convertToProduct(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }

}
