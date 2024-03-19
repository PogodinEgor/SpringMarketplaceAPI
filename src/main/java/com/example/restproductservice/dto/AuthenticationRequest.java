package com.example.restproductservice.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * Класс AuthenticationRequest используется для передачи данных аутентификации пользователя.
 * Содержит информацию, необходимую для аутентификации: имя пользователя, пароль и email.
 * Эти данные используются при регистрации или аутентификации пользователя в системе.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthenticationRequest {
    /**
     * Имя пользователя, используемое для входа в систему.
     * Не может быть пустым, что обеспечивается аннотацией @NotEmpty.
     */
    @NotEmpty(message = "Имя пользователя не может быть пустым")
    private String userName;

    /**
     * Пароль пользователя, используемый для аутентификации.
     * Не может быть пустым, что обеспечивается аннотацией @NotEmpty.
     */
    @NotEmpty(message = "Пароль не может быть пустым")
    private String password;

    /**
     * Электронная почта пользователя.
     * Не может быть пустым, что обеспечивается аннотацией @NotEmpty.
     */
    @NotEmpty(message = "Email не может быть пустым")
    private String email;
}
