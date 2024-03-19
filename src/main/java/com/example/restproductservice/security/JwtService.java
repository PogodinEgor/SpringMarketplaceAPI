package com.example.restproductservice.security;

import com.example.restproductservice.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

/**
 * Сервис для генерации и валидации JWT токенов для аутентификации пользователей.
 */
@Component
public class JwtService {
    /**
     * Секретный ключ для подписи токенов.
     */
    @Value("${your.custom.prefix.SECRET_KEY}")
    private String SECRET_KEY;

    /**
     * Генерирует JWT токен для указанного пользователя.
     * @param user Пользователь, для которого генерируется токен.
     * @return Строковое представление JWT токена.
     */
    public String generateToken(User user){
        return Jwts
                .builder()
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 24*60*60*1000))
                .signWith(getSignKey())
                .compact();

    }

    /**
     * Извлекает имя пользователя из JWT токена.
     * @param token JWT токен.
     * @return Имя пользователя.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Проверяет валидность JWT токена.
     * @param token JWT токен.
     * @param userDetails Данные пользователя.
     * @return true, если токен действителен, иначе false.
     */
    public boolean isValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);

        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Извлекает дату истечения срока действия JWT токена.
     * @param token JWT токен.
     * @return Дата истечения срока действия.
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Проверяет, истек ли срок действия JWT токена.
     * @param token JWT токен.
     * @return true, если срок действия истек, иначе false.
     */    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Извлекает произвольные данные из JWT токена.
     * @param token JWT токен.
     * @param resolver Функция для преобразования Claims в нужный тип данных.
     * @return Произвольные данные из JWT токена.
     */
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    /**
     * Возвращает ключ для подписи и верификации JWT токенов.
     * @return Ключ для подписи.
     */
    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Извлекает все Claims из JWT токена.
     * @param token JWT токен.
     * @return Claims.
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
