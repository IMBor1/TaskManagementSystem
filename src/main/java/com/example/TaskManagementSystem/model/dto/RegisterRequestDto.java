package com.example.TaskManagementSystem.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;
@Getter
@Setter
@NoArgsConstructor
public class RegisterRequestDto {
    @NonNull
    private String email;

    @NonNull
    private String password;
}
