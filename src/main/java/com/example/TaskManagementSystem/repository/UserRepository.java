package com.example.TaskManagementSystem.repository;

import com.example.TaskManagementSystem.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
