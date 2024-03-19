package com.example.restproductservice.exception.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
/**
 * Обработчик исключений доступа, который отправляет кастомный JSON-ответ клиенту,
 * когда доступ к ресурсу запрещен.
 */
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=UTF-8");

        Map<String, Object> data = new HashMap<>();
        data.put("message", "Доступ запрещен");
        data.put("error", accessDeniedException.getMessage());

        String jsonResponse = objectMapper.writeValueAsString(data);
        response.getOutputStream().write(jsonResponse.getBytes(StandardCharsets.UTF_8));
    }
}