package com.example.TaskManagementSystem.config;

import com.example.TaskManagementSystem.model.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * Провайдер для работы с JWT токенами.
 * Отвечает за генерацию, валидацию и извлечение информации из JWT токенов.
 */
@Component
public class JwtTokenProvider {
    private static final String JWT_SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
    private final SecretKey JWT_SECRET_KEY = Keys.hmacShaKeyFor(JWT_SECRET.getBytes());
    private final long JWT_EXPIRATION = 604800000L; // 7 days

    /**
     * Генерирует JWT токен на основе данных аутентификации.
     *
     * @param authentication объект аутентификации, содержащий данные пользователя
     * @return строка JWT токена
     */
    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);

        String role = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("ROLE_USER");

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(JWT_SECRET_KEY)
                .compact();
    }

    /**
     * Проверяет валидность JWT токена.
     *
     * @param token JWT токен для проверки
     * @return true если токен валиден, false в противном случае
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(JWT_SECRET_KEY)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Извлекает email пользователя из JWT токена.
     *
     * @param token JWT токен
     * @return email пользователя
     */
    public String getUserEmailFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(JWT_SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public String getUserRoleFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(JWT_SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("role", String.class);
    }
}