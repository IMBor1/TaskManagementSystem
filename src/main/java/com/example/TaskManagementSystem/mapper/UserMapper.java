package com.example.TaskManagementSystem.mapper;

import com.example.TaskManagementSystem.model.dto.UserDto;
import com.example.TaskManagementSystem.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        return dto;
    }
}