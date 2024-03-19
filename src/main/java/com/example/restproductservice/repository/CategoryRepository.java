package com.example.restproductservice.repository;

import com.example.restproductservice.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



/**
 * Репозиторий для работы с сущностями категорий в базе данных.
 * Предоставляет стандартный набор функционала для CRUD операций над категориями,
 * а также может быть расширен для добавления специфичных методов запросов.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
