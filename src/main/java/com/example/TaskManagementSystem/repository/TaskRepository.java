package com.example.TaskManagementSystem.repository;

import com.example.TaskManagementSystem.model.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findByAuthorEmailOrExecutorEmail(String author_email, String executor_email, Pageable pageable);
}
