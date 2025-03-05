package com.example.TaskManagementSystem.model.entity;


import com.example.TaskManagementSystem.model.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.NonNull;
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String email;
    @NonNull
    private String password;
    @JoinColumn(name = "role_name")
    private Role role;

}
