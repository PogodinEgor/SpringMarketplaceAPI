package com.example.restproductservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * Класс CategoryDTO предназначен для передачи данных о категории товаров между слоями приложения.
 * Он используется для представления данных о категории, включая её наименование и описание,
 * без необходимости загрузки связанных сущностей, таких как список товаров в категории.
 * Это позволяет оптимизировать передачу данных и упрощает работу с информацией о категориях в пользовательском интерфейсе и бизнес-логике.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryDTO{
    /**
     * Наименование категории товаров.
     * Определяет краткое название категории, используемое в интерфейсах и отчетах.
     */
    private String name;

    /**
     * Описание категории товаров.
     * Предоставляет дополнительную информацию о категории, её характеристиках и особенностях.
     */

    private String description;
}