package com.example.restproductservice.dto;
/**
 * Запись ProductFilter служит для представления критериев фильтрации продуктов.
 * Это позволяет легко передавать параметры фильтрации между слоями приложения.
 */
public record ProductFilter(Long categoryId, String name, Double priceLow, Double priceHigh) {
}
