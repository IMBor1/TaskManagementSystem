package com.example.TaskManagementSystem.service;

import com.example.TaskManagementSystem.exception.NotFoundException;
import com.example.TaskManagementSystem.model.dto.RegisterRequestDto;
import com.example.TaskManagementSystem.model.entity.User;
import com.example.TaskManagementSystem.model.enums.Role;
import com.example.TaskManagementSystem.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private  PasswordEncoder passwordEncoder;


    @Override
    public User registerUser(RegisterRequestDto registerRequestDto) {
        log.info("Registering user");
            User user = new User();
            user.setEmail(registerRequestDto.getEmail());
            user.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
            user.setRole(Role.USER);
            return userRepository.save(user);
    }

    @Override
    public User getUserByEmail(String email) {
        log.info("Getting user by email");
        return userRepository.findByEmail(email).orElseThrow(() ->
                new NotFoundException("User with email " + email + " not found"));
    }

    @Override
    public User setRole(User user, Role newRole) {
        log.info("Updating user role");
        User user1 = userRepository.findByEmail(user.getEmail()).orElseThrow(() ->
                new NotFoundException("User with email " + user.getEmail() + " not found"));
        user1.setRole(newRole);
        return userRepository.save(user1);
    }
}
