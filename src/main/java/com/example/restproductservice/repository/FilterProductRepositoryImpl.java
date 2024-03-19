package com.example.restproductservice.repository;

import com.example.restproductservice.dto.ProductFilter;
import com.example.restproductservice.model.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
/**
 * Реализация пользовательского репозитория для выполнения фильтрации продуктов.
 * Использует {@link EntityManager} для создания и выполнения динамических запросов
 * на основе критериев, заданных в {@link ProductFilter}.
 *
 * Предоставляет метод {@code findAllByFilter}, который позволяет получить список продуктов,
 * соответствующих заданным фильтрам.
 */
@RequiredArgsConstructor
public class FilterProductRepositoryImpl implements FilterProductRepository{

    private final EntityManager entityManager;

    /**
     * Возвращает список продуктов, соответствующих заданным критериям фильтрации.
     * Поддерживает фильтрацию по категории, имени продукта, минимальной и максимальной цене.
     *
     * @param filter Объект {@link ProductFilter}, содержащий критерии фильтрации.
     * @return Список продуктов, соответствующих заданным критериям.
     */
    @Override
    public List<Product> findAllByFilter(ProductFilter filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteria = cb.createQuery(Product.class);

        Root<Product> product = criteria.from(Product.class);
        criteria.select(product);
        List<Predicate> predicates = new ArrayList<>();
        if(filter.categoryId() != null){
            predicates.add(cb.equal(product.get("category").get("id"), filter.categoryId()));
        }
        if (filter.name() != null && !filter.name().isEmpty()) {
            predicates.add(cb.like(cb.lower(product.get("name")), "%" + filter.name().toLowerCase() + "%"));
        }
        if (filter.priceLow() != null) {
            predicates.add(cb.greaterThanOrEqualTo(product.get("price"), filter.priceLow()));
        }
        if (filter.priceHigh() != null) {
            predicates.add(cb.lessThanOrEqualTo(product.get("price"), filter.priceHigh()));
        }
        criteria.where(predicates.toArray(Predicate[]::new));

        return entityManager.createQuery(criteria).getResultList();
    }
}
