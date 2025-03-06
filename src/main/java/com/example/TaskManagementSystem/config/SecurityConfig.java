package com.example.TaskManagementSystem.config;

import com.example.TaskManagementSystem.model.enums.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@OpenAPIDefinition(
    info = @Info(
        title = "Task Management System API",
        version = "1.0",
        description = "API for managing tasks"
    )
)
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) throws Exception {
        http
                .csrf(csrf -> {
                    try {
                        csrf.disable()
                                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                                        .requestMatchers("/auth/**").permitAll()
                                        .requestMatchers("/admin/**").hasRole(Role.ADMIN.name())
                                        .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                                        .requestMatchers("/tasks/**").authenticated()
                                        .requestMatchers("/users/**").authenticated()
                                        .anyRequest().authenticated()
                                )
                                .sessionManagement(sessionManagement -> sessionManagement
                                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });

        http.addFilterBefore(jwtFilter(jwtTokenProvider,userDetailsService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public JwtAuthenticationFilter jwtFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
        return new JwtAuthenticationFilter(jwtTokenProvider, userDetailsService);
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();

    }
}