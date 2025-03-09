package com.example.TaskManagementSystem.service;

import com.example.TaskManagementSystem.exception.NotFoundException;
import com.example.TaskManagementSystem.model.dto.RegisterRequestDto;
import com.example.TaskManagementSystem.model.entity.User;
import com.example.TaskManagementSystem.model.enums.Role;
import com.example.TaskManagementSystem.repository.UserRepository;
import com.example.TaskManagementSystem.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private RegisterRequestDto registerRequestDto;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("test@test.com");
        user.setPassword("encodedPassword");
        user.setRole(Role.ADMIN);

        registerRequestDto = new RegisterRequestDto();
        registerRequestDto.setEmail("test@test.com");
        registerRequestDto.setPassword("password");
    }

    @Test
    void registerUser_Success() {
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.registerUser(registerRequestDto);

        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(Role.ADMIN, result.getRole());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void getUserByEmail_Success() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        User result = userService.getUserByEmail("test@test.com");

        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());
    }

    @Test
    void getUserByEmail_NotFound() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.getUserByEmail("test@test.com"));
    }

    @Test
    void setRole_Success() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.setRole(1L, Role.USER);

        assertNotNull(result);
        assertEquals(Role.USER, result.getRole());
        verify(userRepository).save(any(User.class));
    }
} 