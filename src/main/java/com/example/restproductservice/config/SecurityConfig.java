package com.example.restproductservice.config;

import com.example.restproductservice.exception.security.CustomAccessDeniedHandler;
import com.example.restproductservice.exception.security.CustomAuthenticationEntryPoint;
import com.example.restproductservice.filter.JwtFilter;
import com.example.restproductservice.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Класс SecurityConfig конфигурирует настройки безопасности для веб-приложения.
 * Определяет правила доступа к различным частям приложения, конфигурацию фильтров аутентификации,
 * а также настройки обработки исключений безопасности и сессий.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    /**
     * Конфигурирует цепочку фильтров безопасности для определения правил доступа.
     *
     * @param http Объект HttpSecurity для настройки безопасности на уровне HTTP.
     * @return Сконфигурированную цепочку фильтров безопасности.
     * @throws Exception Исключение, которое может возникнуть во время конфигурирования.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        req->req.requestMatchers("/auth/login**","/auth/register**").permitAll()
                                .requestMatchers("/swagger-ui/**", "/swagger-resources/*", "/v3/api-docs/**").permitAll()
                                .requestMatchers("/category/all","/products/search").hasAnyAuthority("USER", "ADMIN")
                                .requestMatchers("/auth/**","/category/**", "/products/**").hasAnyAuthority("ADMIN")
                                .anyRequest()
                                .authenticated()
                ).userDetailsService(userDetailsServiceImpl)
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(
                        e->e.accessDeniedHandler(new CustomAccessDeniedHandler())
                                .authenticationEntryPoint(new CustomAuthenticationEntryPoint()))
                .build();
    }

    /**
     * Создаёт бин обработчика отказа в доступе.
     *
     * @return Экземпляр CustomAccessDeniedHandler.
     */
    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler(){
        return new CustomAccessDeniedHandler();
    }

    /**
     * Создаёт бин точки входа для аутентификации.
     *
     * @return Экземпляр CustomAuthenticationEntryPoint.
     */
    @Bean
    public CustomAuthenticationEntryPoint customAuthenticationEntryPoint(){
        return new CustomAuthenticationEntryPoint();
    }

    /**
     * Создаёт бин провайдера аутентификации.
     *
     * @return Экземпляр AuthenticationProvider.
     */
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsServiceImpl);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    /**
     * Создаёт бин для кодирования паролей.
     *
     * @return Экземпляр PasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Создаёт бин менеджера аутентификации.
     *
     * @param configuration Конфигурация для аутентификации.
     * @return Экземпляр AuthenticationManager.
     * @throws Exception Исключение, которое может возникнуть во время создания.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
