package com.example.TaskManagementSystem.controller;

import com.example.TaskManagementSystem.mapper.UserMapper;
import com.example.TaskManagementSystem.model.dto.UserDto;
import com.example.TaskManagementSystem.model.entity.User;
import com.example.TaskManagementSystem.model.enums.Role;
import com.example.TaskManagementSystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok().build();
    }
    @PutMapping("{id}")
    public ResponseEntity<UserDto> updateUserRole(
            @PathVariable Long id,
            @RequestParam Role role) {
        User updatedUser = userService.setRole(id, role);
        UserDto userDto = userMapper.toDto(updatedUser);
        return ResponseEntity.ok(userDto);


    }
}
