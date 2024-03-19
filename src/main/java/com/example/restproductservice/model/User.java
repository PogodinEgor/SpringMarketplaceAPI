package com.example.restproductservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * Класс представляет сущность "Пользователь" в системе.
 * Хранит информацию о пользователе, включая его уникальные идентификатор, имя пользователя, пароль, email и роль.
 */
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    /**
     * Уникальный идентификатор пользователя.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Имя пользователя, используемое для входа в систему. Уникальное значение.
     */
    @Column(name = "username",unique = true, nullable = false)
    private String username;

    /**
     * Пароль пользователя, используемый для аутентификации.
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * Электронная почта пользователя. Используется для связи и восстановления доступа. Уникальное значение.
     */
    @Column(name = "email",unique = true, nullable = false)
    private String email;

    /**
     * Роль пользователя в системе, определяющая уровень доступа к ресурсам.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

}
