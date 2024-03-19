package com.example.restproductservice.controller;

import com.example.restproductservice.dto.CategoryDTO;
import com.example.restproductservice.model.Category;
import com.example.restproductservice.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для управления категориями товаров.
 * Позволяет создавать новые категории, удалять и обновлять существующие, а также получать список всех категорий.
 */
@Tag(name = "Category Controller",
        description = "Контроллер для управления категориями. Позволяет создавать, удалять, обновлять категории и получать список всех категорий.")
@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    /**
     * Создание новой категории.
     *
     * @param categoryDTO DTO категории, содержащее информацию для создания.
     * @return HTTP статус OK при успешном создании категории.
     */
    @Operation(summary = "Создание новой категории",
            description = "Метод позволяет создать новую категорию, используя данные, предоставленные в теле запроса.")
    @PostMapping("/create")
    public ResponseEntity<HttpStatus> createProduct(@RequestBody CategoryDTO categoryDTO) {
        categoryService.save(convertToCategory(categoryDTO));
        return ResponseEntity.ok(HttpStatus.OK);

    }

    /**
     * Удаление категории по идентификатору.
     *
     * @param id Идентификатор категории для удаления.
     * @return HTTP статус OK при успешном удалении.
     */
    @Operation(summary = "Удаление категории по идентификатору",
            description = "Метод удаляет категорию по указанному идентификатору.")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteCategory(@PathVariable("id") long id) {
        categoryService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * Обновление информации о категории.
     *
     * @param id Идентификатор категории, информацию о которой необходимо обновить.
     * @param categoryDTO DTO категории с новой информацией.
     * @return HTTP статус OK при успешном обновлении данных о категории.
     */
    @Operation(summary = "Обновление категории",
            description = "Метод обновляет информацию о категории по указанному идентификатору, используя данные, предоставленные в теле запроса.")
    @PatchMapping("/update/{id}")
    public ResponseEntity<HttpStatus> updateCategory(@PathVariable("id") long id, @RequestBody CategoryDTO categoryDTO) {
        categoryService.update(id, convertToCategory(categoryDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * Получение списка всех категорий.
     *
     * @return Список всех категорий в формате ResponseEntity.
     */
    @Operation(summary = "Получение списка всех категорий",
            description = "Метод возвращает список всех существующих категорий.")
    @GetMapping("/all")
    public ResponseEntity<?> getAllCategories() {
        List<Category> categories = categoryService.findByAll();

        if (categories != null) {
            return ResponseEntity.ok(categories);
        }
        return ResponseEntity.noContent().build();
    }

    /**
     * Преобразование CategoryDTO в сущность Category.
     *
     * @param categoryDTO DTO категории для преобразования.
     * @return Сущность Category.
     */
    private Category convertToCategory(CategoryDTO categoryDTO) {
        return modelMapper.map(categoryDTO, Category.class);
    }

}
