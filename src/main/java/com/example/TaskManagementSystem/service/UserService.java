package com.example.TaskManagementSystem.service;

import com.example.TaskManagementSystem.model.dto.RegisterRequestDto;
import com.example.TaskManagementSystem.model.entity.User;
import com.example.TaskManagementSystem.model.enums.Role;

public interface UserService {
    User registerUser(RegisterRequestDto registerRequestDto);

    User getUserByEmail(String email);

    User setRole(Long userId, Role newRole);
    public User getUserById(Long id);
}
