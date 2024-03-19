package com.example.restproductservice.service;

import com.example.restproductservice.dto.ProductFilter;
import com.example.restproductservice.exception.CategoryNotFoundException;
import com.example.restproductservice.exception.NotCategoriesException;
import com.example.restproductservice.exception.ProductNotFoundException;
import com.example.restproductservice.model.Product;
import com.example.restproductservice.repository.CategoryRepository;
import com.example.restproductservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для управления продуктами.
 * Позволяет создавать, обновлять, удалять и получать продукты, а также фильтровать их по различным критериям.
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    /**
     * Сохраняет новый продукт в базе данных. Проверяет существование категории перед сохранением.
     * @param product Продукт для сохранения.
     * @throws NotCategoriesException Если указанная категория не существует.
     */
    @Transactional
    public void save(Product product) {
        if (!categoryRepository.existsById(product.getId())){
            throw new NotCategoriesException("При создании продукта выбранна несуществующая категория");
        }
        if (product.getCategory() != null ) {
            productRepository.save(product);
        }else {
            throw new NotCategoriesException("При создании продукта не выбрана категория");}

    }

    /**
     * Удаляет продукт по идентификатору.
     * @param id Идентификатор продукта для удаления.
     */
    @Transactional
    public void delete(long id) {
        productRepository.deleteById(id);
    }

    /**
     * Обновляет информацию о продукте на основе предоставленных данных.
     * @param id Идентификатор продукта для обновления.
     * @param newProduct Новые данные продукта.
     * @return Обновленный продукт.
     * @throws ProductNotFoundException Если продукт с указанным ID не найден.
     * @throws CategoryNotFoundException Если указанная категория в новых данных не существует.
     */
    @Transactional
    public Product update(long id, Product newProduct) {
        Product oldProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(String.format("Продукта с таким id: %s не существует", id)));

        updateProductFields(newProduct, oldProduct);

        if (newProduct.getCategory() != null && !categoryRepository.existsById(newProduct.getCategory().getId())) {
            throw new CategoryNotFoundException("Указанная категория не существует.");
        }

        return productRepository.save(oldProduct);
    }

    /**
     * Вспомогательный метод для обновления полей продукта.
     * @param newProduct Новый продукт с обновленными данными.
     * @param oldProduct Существующий продукт, поля которого будут обновлены.
     */
    private static void updateProductFields(Product newProduct, Product oldProduct) {
        oldProduct.setName(newProduct.getName());
        oldProduct.setDescription(newProduct.getDescription());
        oldProduct.setPrice(newProduct.getPrice());
        oldProduct.setImage(newProduct.getImage());
        oldProduct.setCategory(newProduct.getCategory());
        oldProduct.setActive(newProduct.isActive());
    }

    /**
     * Возвращает список продуктов, отфильтрованных по заданным критериям.
     * @param filter Критерии фильтрации продуктов.
     * @return Список отфильтрованных продуктов.
     */
    public List<Product> findByAllFilter(ProductFilter filter) {
        return productRepository.findAllByFilter(filter);
    }


    /**
     * Возвращает продукт по его идентификатору.
     * @param id Идентификатор продукта.
     * @return Найденный продукт.
     * @throws ProductNotFoundException Если продукт с указанным ID не найден.
     */
    public Product findById(long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElseThrow(() -> new ProductNotFoundException(String.format("Продукта с таким id: %s не существует", id)));
    }
}
