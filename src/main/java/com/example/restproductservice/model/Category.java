package com.example.restproductservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.HashSet;
import java.util.Set;

/**
 * Класс представляет сущность "Категория" в системе.
 * Хранит информацию о категории товаров, включая её уникальный идентификатор, наименование, описание и набор товаров, принадлежащих к этой категории.
 */
@Entity
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Category {
    /**
     * Уникальный идентификатор категории.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Наименование категории. Должно быть непустым.
     */
    @NotEmpty(message = "Наименование категории не может быть пустым")
    @Column(name = "name")
    private String name;

    /**
     * Описание категории. Может быть пустым.
     */
    @Column(name = "description")
    private String description;

    /**
     * Набор товаров, принадлежащих к этой категории.
     * Использует ленивую загрузку для оптимизации производительности и предотвращает рекурсивную загрузку с помощью аннотации @JsonManagedReference.
     */
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    @JsonIgnore
    private Set<Product> products = new HashSet<>();

}