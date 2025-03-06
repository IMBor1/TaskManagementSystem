package com.example.TaskManagementSystem.service;

import com.example.TaskManagementSystem.model.dto.AuthRequestDto;
import com.example.TaskManagementSystem.model.dto.AuthResponseDto;

public interface AuthenticationService {
    AuthResponseDto login(AuthRequestDto authRequest);
}
