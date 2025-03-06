package com.example.TaskManagementSystem.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
@Data
@NoArgsConstructor
public class RegisterRequestDto {
    @NonNull
    private String email;

    @NonNull
    private String password;

}
