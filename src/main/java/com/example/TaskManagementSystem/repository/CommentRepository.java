package com.example.TaskManagementSystem.repository;

import com.example.TaskManagementSystem.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
