package com.example.TaskManagementSystem.service;

import com.example.TaskManagementSystem.exception.NotFoundException;
import com.example.TaskManagementSystem.model.dto.RegisterRequestDto;
import com.example.TaskManagementSystem.model.entity.User;
import com.example.TaskManagementSystem.model.enums.Role;
import com.example.TaskManagementSystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
//    private  PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(RegisterRequestDto registerRequestDto) {
        log.info("Registering user");
        User user = new User();
        user.setEmail(registerRequestDto.getEmail());
        //           user.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
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
    public User setRole(Long userId, Role newRole) {
        log.info("Updating user role");
        User user1 = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("User with email " + userId + " not found"));
        user1.setRole(newRole);
        return userRepository.save(user1);
    }
    public User getUserById(long id) {
        log.info("Getting user by id");
        return userRepository.findById(id).orElseThrow(() ->
                new NotFoundException("User with id " + id + " not found"));
    }
}
