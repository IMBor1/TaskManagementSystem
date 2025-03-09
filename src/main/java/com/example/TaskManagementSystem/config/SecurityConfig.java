package com.example.TaskManagementSystem.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Конфигурация безопасности приложения.
 * Настраивает Spring Security, JWT аутентификацию и документацию API.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@OpenAPIDefinition(
    info = @Info(
        title = "Task Management System API",
        version = "1.0",
        description = "API для управления задачами"
    )
)
@SecurityScheme(
    name = "Bearer Authentication",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer"
)
public class SecurityConfig {

    /**
     * Конфигурирует цепочку фильтров безопасности.
     *
     * @param http конфигурация HTTP безопасности
     * @param jwtAuthenticationFilter фильтр JWT аутентификации
     * @return настроенная цепочка фильтров
     * @throws Exception если возникла ошибка при конфигурации
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    /**
     * Создает фильтр JWT аутентификации.
     *
     * @param jwtTokenProvider провайдер JWT токенов
     * @param userDetailsService сервис пользовательских данных
     * @return настроенный фильтр JWT аутентификации
     */
    @Bean
    public JwtAuthenticationFilter jwtFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
        return new JwtAuthenticationFilter(jwtTokenProvider, userDetailsService);
    }

    /**
     * Создает кодировщик паролей.
     *
     * @return BCrypt кодировщик паролей
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}