package com.example.restproductservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * Класс AuthorizationRequest предназначен для передачи данных аутентификации пользователя.
 * Этот класс используется при процедуре авторизации, где необходимы только имя пользователя и пароль.
 * Он служит контейнером для этих данных и передаёт их в систему для проверки подлинности.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthorizationRequest {

    /**
     * Имя пользователя, используемое для идентификации при авторизации.
     * Может быть пустым, но это может привести к отказу в авторизации.
     */
    private String userName;
    /**
     * Пароль пользователя, используемый для верификации при авторизации.
     * Может быть пустым, но это может привести к отказу в авторизации.
     */
    private String password;
}
