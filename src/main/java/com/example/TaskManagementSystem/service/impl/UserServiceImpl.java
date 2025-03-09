package com.example.TaskManagementSystem.service.impl;

import com.example.TaskManagementSystem.exception.NotFoundException;
import com.example.TaskManagementSystem.model.dto.RegisterRequestDto;
import com.example.TaskManagementSystem.model.entity.User;
import com.example.TaskManagementSystem.model.enums.Role;
import com.example.TaskManagementSystem.repository.UserRepository;
import com.example.TaskManagementSystem.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Реализация сервиса для управления пользователями.
 * Предоставляет методы для регистрации, получения информации и управления ролями пользователей.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Регистрирует нового пользователя.
     *
     * @param registerRequestDto данные для регистрации
     * @return созданный пользователь
     */
    @Override
    public User registerUser(RegisterRequestDto registerRequestDto) {
        log.info("Registering user");
        User user = new User();
        user.setEmail(registerRequestDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
        user.setRole(Role.ADMIN);
        return userRepository.save(user);
    }

    /**
     * Получает пользователя по email.
     *
     * @param email email пользователя
     * @return найденный пользователь
     * @throws NotFoundException если пользователь не найден
     */
    @Override
    public User getUserByEmail(String email) {
        log.info("Getting user by email");
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User with email " + email + " not found"));
    }

    /**
     * Устанавливает новую роль пользователю.
     *
     * @param userId ID пользователя
     * @param newRole новая роль
     * @return обновленный пользователь
     * @throws NotFoundException если пользователь не найден
     */
    @Override
    public User setRole(Long userId, Role newRole) {
        log.info("Updating user role");
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with ID " + userId + " not found"));
        user.setRole(newRole);
        return userRepository.save(user);
    }

    /**
     * Получает пользователя по ID.
     *
     * @param id ID пользователя
     * @return найденный пользователь
     * @throws NotFoundException если пользователь не найден
     */
    @Override
    public User getUserById(Long id) {
        log.info("Getting user by id");
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
    }
}
