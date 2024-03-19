package com.example.restproductservice.exception.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Точка входа для кастомной аутентификации, которая отправляет JSON-ответ,
 * когда аутентификация неудачна.
 */
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        String json = "{\"error\": \"Недопустимый доступ. Пожалуйста, авторизуйтесь.\"}";
        response.getOutputStream().write(json.getBytes(StandardCharsets.UTF_8));
    }
}

