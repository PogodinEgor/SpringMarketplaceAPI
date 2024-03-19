package com.example.restproductservice.service;

import com.example.restproductservice.dto.AuthenticationResponse;
import com.example.restproductservice.exception.EmailAlreadyExistsException;
import com.example.restproductservice.exception.InvalidRegistrationDataException;
import com.example.restproductservice.exception.UsernameAlreadyExistsException;
import com.example.restproductservice.model.Role;
import com.example.restproductservice.model.User;
import com.example.restproductservice.repository.UserRepository;
import com.example.restproductservice.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;
/**
 * Сервис аутентификации предоставляет функциональность для регистрации новых пользователей и входа в систему существующих пользователей.
 * Он использует UserRepository для взаимодействия с базой данных пользователей, JwtService для генерации токенов JWT, PasswordEncoder для хеширования паролей,
 * и AuthenticationManager для управления процессом аутентификации.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * Регистрирует нового пользователя в системе. Проверяет, существует ли уже пользователь с таким же email или именем пользователя.
     * В случае успеха, сохраняет нового пользователя в базе данных.
     * @param request Объект User, содержащий информацию о новом пользователе.
     * @throws EmailAlreadyExistsException Если пользователь с таким email уже зарегистрирован.
     * @throws UsernameAlreadyExistsException Если пользователь с таким именем пользователя уже зарегистрирован.
     * @throws InvalidRegistrationDataException Если предоставленные данные некорректны или неполны.
     */
    @Transactional
    public void register(User request) {
        if(userRepository.existsByEmail(request.getEmail())){
            throw new EmailAlreadyExistsException(String.format("Пользователь с таким email: %s уже зарегестрирован", request.getEmail()));
        }

         if(userRepository.existsByUsername(request.getUsername())) {
           throw new UsernameAlreadyExistsException(String.format("Пользователь с таким именем: %s уже зарегестрирован", request.getUsername()));
        }

        processRegistration(request);

    }

    /**
     * Вспомогательный метод для обработки регистрации нового пользователя.
     * @param request Объект User, содержащий информацию о регистрируемом пользователе.
     */
    private void processRegistration(User request) {
        if (isRequestDataInvalid(request)) {
            throw new InvalidRegistrationDataException("Предоставленные данные некорректны или неполны.");
        }

        User user = new User();
        user.setUsername(request.getUsername().trim());
        user.setPassword(passwordEncoder.encode(request.getPassword().trim()));
        user.setEmail(request.getEmail().trim());
        user.setRole(Role.USER);
        userRepository.save(user);
    }

    /**
     * Проверяет валидность данных запроса на регистрацию.
     * @param request Объект User, содержащий данные для регистрации.
     * @return true, если данные невалидны; false, если данные валидны.
     */
    private boolean isRequestDataInvalid(User request) {
        return Stream.of(request.getUsername(), request.getPassword(), request.getEmail())
                .anyMatch(input -> input == null || input.isBlank());
    }

    /**
     * Выполняет вход пользователя в систему. Аутентифицирует пользователя на основе имени пользователя и пароля.
     * В случае успеха, генерирует и возвращает JWT токен.
     * @param userRequest Объект User, содержащий имя пользователя и пароль для аутентификации.
     * @return Объект AuthenticationResponse, содержащий сгенерированный JWT токен.
     * @throws UsernameNotFoundException Если пользователь с таким именем пользователя не найден.
     */
    public AuthenticationResponse login(User userRequest){
       User user =  userRepository.findByUsername(userRequest.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException(String.format("Пользователь c таким именем: %s не найден",  userRequest.getUsername())));

       Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword()));

       SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtService.generateToken(user);

        return new AuthenticationResponse(jwt);
    }
}
