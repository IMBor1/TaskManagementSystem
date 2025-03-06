package com.example.TaskManagementSystem.controller;

import com.example.TaskManagementSystem.model.dto.TaskDto;
import com.example.TaskManagementSystem.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {
    private final TaskService taskService;

    @GetMapping("/tasks")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks(null, null, 0, 10).getContent());
    }
}