package com.example.TaskManagementSystem.model.dto;

import com.example.TaskManagementSystem.model.enums.Role;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String email;
    private Role role;
}
