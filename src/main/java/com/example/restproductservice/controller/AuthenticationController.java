package com.example.restproductservice.controller;

import com.example.restproductservice.dto.AuthenticationRequest;
import com.example.restproductservice.dto.AuthenticationResponse;
import com.example.restproductservice.dto.AuthorizationRequest;
import com.example.restproductservice.model.User;
import com.example.restproductservice.service.AuthenticationService;
import com.example.restproductservice.service.UserDetailsServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для аутентификации и регистрации пользователей.
 * Обеспечивает функциональность для входа в систему, регистрации новых пользователей,
 * получения списка всех пользователей и получения информации о пользователе по его ID.
 */
@Tag(name = "Authentication Controller", description = "Контроллер для аутентификации и регистрации пользователей.")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationService authenticationService;
    private final ModelMapper modelMapper;

    /**
     * Вход пользователя в систему.
     * Аутентифицирует пользователя и возвращает JWT токен.
     *
     * @param authorizationRequest Данные для аутентификации пользователя.
     * @return ResponseEntity с JWT токеном или сообщением об ошибке.
     */
    @Operation(summary = "Вход пользователя", description = "Аутентификация пользователя и возврат JWT токена.")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthorizationRequest authorizationRequest) {
        AuthenticationResponse login = authenticationService.login(convertToUser(authorizationRequest));
        return ResponseEntity.ok(login);
    }

    /**
     * Регистрация нового пользователя в системе.
     *
     * @param authenticationRequest Данные для регистрации нового пользователя.
     * @return ResponseEntity с статусом OK при успешной регистрации.
     */
    @Operation(summary = "Регистрация пользователя", description = "Регистрация нового пользователя в системе.")
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody  AuthenticationRequest authenticationRequest) {


        authenticationService.register(convertToUser(authenticationRequest));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * Получение списка всех зарегистрированных пользователей.
     *
     * @return ResponseEntity со списком всех пользователей.
     */
    @Operation(summary = "Получение списка всех пользователей", description = "Возвращает список всех зарегистрированных пользователей.")
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userDetailsService.getAllUser());
    }

    /**
     * Получение пользователя по уникальному идентификатору.
     *
     * @param id Уникальный идентификатор пользователя.
     * @return ResponseEntity с данными пользователя.
     */
    @Operation(summary = "Получение пользователя по ID", description = "Возвращает пользователя по уникальному идентификатору.")
    @GetMapping("/getUser{id}")
    public ResponseEntity<?> getUserById(@PathVariable long id) {
        return ResponseEntity.ok(userDetailsService.findByUserId(id));
    }

    /**
     * Конвертирует объект AuthenticationRequest в сущность User.
     *
     * @param authRequest Объект запроса аутентификации.
     * @return Сконвертированный объект User.
     */
    public User convertToUser(AuthenticationRequest authRequest) {
        return modelMapper.map(authRequest, User.class);
    }

    /**
     * Конвертирует объект AuthorizationRequest в сущность User.
     *
     * @param authorizationRequest Объект запроса авторизации.
     * @return Сконвертированный объект User.
     */
    public User convertToUser(AuthorizationRequest authorizationRequest) {return modelMapper.map(authorizationRequest, User.class);}

}
