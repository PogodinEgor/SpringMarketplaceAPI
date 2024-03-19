package com.example.restproductservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Класс представляет сущность "Продукт" в системе.
 * Содержит информацию о продукте, включая его уникальный идентификатор, наименование, описание, цену, изображение,
 * категорию, к которой продукт принадлежит, дату добавления продукта и статус активности.
 */
@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {
    /**
     * Уникальный идентификатор продукта.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Наименование продукта. Не может быть пустым.
     */
    @NotEmpty(message = "Наименование продукта не может быть пустым")
    @Column(name = "name")
    private String name;

    /**
     * Описание продукта.
     */
    @Column(name = "description")
    private String description;

    /**
     * Цена продукта.
     */
    @Column(name = "price")
    private double price;

    /**
     * Ссылка на изображение продукта.
     */
    @Column(name = "image")
    private String image;

    /**
     * Категория, к которой принадлежит продукт.
     * Определена отношением "многие к одному" с использованием внешнего ключа "category_id".
     */
    @NotNull(message = "Выберете категорию!")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @JsonBackReference
    private Category category;

    /**
     * Дата добавления продукта в систему.
     */
    @Column(name = "added_date")
    private Date addedDate;

    /**
     * Флаг активности продукта в системе.
     * True означает, что продукт активен и доступен для покупки.
     */
    @Column(name = "is_active")
    private boolean isActive;

}