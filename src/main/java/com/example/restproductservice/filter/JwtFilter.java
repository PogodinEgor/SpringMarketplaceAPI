package com.example.restproductservice.filter;

import com.example.restproductservice.security.JwtService;
import com.example.restproductservice.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Фильтр, который проверяет наличие JWT токена в заголовке запроса и аутентифицирует пользователя,
 * если токен действителен.
 */
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    /**
     * Сервис для работы с JWT токенами.
     */
    private final JwtService jwtService;

    /**
     * Сервис для работы с данными пользователей.
     */
    private final UserDetailsServiceImpl userService;

    /**
     * Осуществляет фильтрацию каждого запроса.
     *
     * @param request     Запрос от клиента.
     * @param response    Ответ сервера.
     * @param filterChain Цепочка фильтров.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Извлекаем заголовок Authorization из запроса
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String userName = null;

        // Если заголовок содержит токен, извлекаем имя пользователя из токена
        if (authHeader != null && authHeader.startsWith("Bearer")) {
            token = authHeader.substring(7);
            userName = jwtService.extractUsername(token);
        }

        // Если имя пользователя не null и аутентификация не была установлена, проверяем валидность токена
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.loadUserByUsername(userName);

            // Если токен действителен, устанавливаем аутентификацию в контексте Spring Security
            if (jwtService.isValid(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);

    }
}
