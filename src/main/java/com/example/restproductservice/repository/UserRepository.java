package com.example.restproductservice.repository;

import com.example.restproductservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для управления сущностями пользователей в базе данных.
 * Расширяет {@link JpaRepository} для предоставления стандартного набора операций CRUD.
 *
 * Позволяет выполнять поиск пользователей по имени пользователя и email,
 * а также проверять наличие пользователей с заданным именем пользователя или email в базе данных.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Находит пользователя по его имени пользователя.
     *
     * @param username Имя пользователя.
     * @return Optional, содержащий найденного пользователя, если он существует, иначе пустой Optional.
     */
    Optional<User> findByUsername(String username);

    /**
     * Проверяет существование пользователя с заданным именем пользователя в базе данных.
     *
     * @param username Имя пользователя для проверки.
     * @return true, если пользователь с таким именем существует, иначе false.
     */
    boolean existsByUsername(String username);

    /**
     * Проверяет существование пользователя с заданным email в базе данных.
     *
     * @param email Email пользователя для проверки.
     * @return true, если пользователь с таким email существует, иначе false.
     */
    boolean existsByEmail(String email);
}
