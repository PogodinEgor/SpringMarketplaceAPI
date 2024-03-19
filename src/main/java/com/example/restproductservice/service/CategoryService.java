package com.example.restproductservice.service;

import com.example.restproductservice.exception.CategoryContainsActiveProductsException;
import com.example.restproductservice.exception.CategoryNotFoundException;
import com.example.restproductservice.model.Category;
import com.example.restproductservice.model.Product;
import com.example.restproductservice.repository.CategoryRepository;
import com.example.restproductservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * Сервис для управления категориями продуктов.
 * Позволяет создавать, обновлять, получать и удалять категории.
 * Включает в себя дополнительные проверки, например, для удаления категорий.
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    /**
     * Сохраняет новую категорию в базе данных.
     * @param category Категория для сохранения.
     */
    @Transactional
    public void save(Category category) {
        categoryRepository.save(category);
    }

    /**
     * Возвращает список всех категорий.
     * @return Список категорий.
     */
    public List<Category> findByAll(){
        return categoryRepository.findAll();
    }

    /**
     * Обновляет информацию о категории на основе предоставленных данных.
     * @param id Идентификатор категории для обновления.
     * @param newCategory Новые данные категории.
     * @return Обновленная категория.
     */
    @Transactional
    public Category update(long id, Category newCategory){
        Category oldCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(String.format("Категории с таким id: %s не существует", id)));
        updateCategoryFields(newCategory, oldCategory);

        return categoryRepository.save(oldCategory);
    }

    /**
     * Вспомогательный метод для обновления полей категории.
     * @param newCategory Новая категория с обновленными данными.
     * @param oldCategory Существующая категория, поля которой будут обновлены.
     */
    private static void updateCategoryFields(Category newCategory, Category oldCategory) {
        oldCategory.setName(newCategory.getName());
        oldCategory.setDescription(newCategory.getDescription());
    }

    /**
     * Удаляет категорию, если в ней нет активных продуктов.
     * @param id Идентификатор категории для удаления.
     * @throws CategoryContainsActiveProductsException Если категория содержит активные продукты.
     */
    @Transactional
    public void delete(long id){
    List<Product> products = productRepository.findByCategoryId(id);
        boolean allProductsInactive =products.isEmpty() || products.stream().noneMatch(Product::isActive);
        if(allProductsInactive){
            categoryRepository.deleteById(id);
        }else {
            throw new CategoryContainsActiveProductsException("Невозможно удалить категорию, так как в ней есть активные продукты.");
        }
    }

}
