package com.example.restproductservice.service;

import com.example.restproductservice.exception.UserNotFoundException;
import com.example.restproductservice.model.User;
import com.example.restproductservice.repository.UserRepository;
import com.example.restproductservice.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
/**
 * Сервис для загрузки данных пользователя по его имени пользователя.
 * Используется для аутентификации и авторизации пользователей в системе.
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    /**
     * Загружает данные пользователя по имени пользователя.
     * @param username Имя пользователя.
     * @return Детали пользователя, используемые Spring Security для аутентификации и авторизации.
     * @throws UsernameNotFoundException Если пользователь с указанным именем пользователя не найден.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(UserDetailsImpl::new).orElseThrow(() -> new UsernameNotFoundException(String.format("Пользователь c таким именем: %s не найден",  username)));
    }

    /**
     * Возвращает список всех зарегистрированных пользователей в системе.
     * @return Список пользователей.
     * @throws UserNotFoundException Если в системе не зарегистрированы пользователи.
     */
    @Transactional(readOnly = true)
    public List<User> getAllUser(){
        List<User> users = userRepository.findAll();
        if(users.isEmpty()){
            throw new UserNotFoundException("Зарегистрированных пользователей нет");
        }
        return users;
    }
    /**
     * Находит пользователя по его идентификатору.
     * @param id Идентификатор пользователя.
     * @return Пользователь, соответствующий указанному идентификатору.
     * @throws UserNotFoundException Если пользователь с указанным идентификатором не найден.
     */
    @Transactional(readOnly = true)
    public User findByUserId(long id){
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(String.format("Пользователь с таким id: %s не найден", id)));
    }
}
