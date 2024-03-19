package com.example.restproductservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * Класс AuthenticationResponse предназначен для передачи ответа после успешной аутентификации пользователя.
 * Содержит сгенерированный токен доступа, который используется для авторизации пользователя в системе.
 * Этот токен необходимо предоставлять при выполнении запросов к защищенным ресурсам.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthenticationResponse {
    /**
     * Токен доступа, выдаваемый после успешной аутентификации пользователя.
     * Используется для авторизации пользователя и подтверждения его прав на доступ к ресурсам системы.
     */
    private String token;
}
