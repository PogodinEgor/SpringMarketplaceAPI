package com.example.restproductservice.repository;

import com.example.restproductservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для взаимодействия с сущностями продуктов в базе данных.
 * Расширяет {@link JpaRepository} для обеспечения стандартного набора CRUD операций,
 * и {@link FilterProductRepository} для поддержки дополнительных операций фильтрации продуктов.
 *
 * Позволяет извлекать данные о продуктах по различным критериям, включая поиск по категории.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, FilterProductRepository {


    /**
     * Находит список продуктов по идентификатору категории.
     *
     * @param id Идентификатор категории, по которой производится поиск продуктов.
     * @return Список продуктов, принадлежащих заданной категории.
     */
    List<Product> findByCategoryId(long id);
}
