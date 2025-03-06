package com.example.TaskManagementSystem.controller;

import com.example.TaskManagementSystem.model.dto.TaskDto;
import com.example.TaskManagementSystem.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;


    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks(@RequestParam String author,
                                                     @RequestParam String executor,
                                                     @RequestParam (defaultValue = "0") int page,
                                                     @RequestParam (defaultValue = "10") int size) {
        Page<TaskDto> tasks = taskService.getAllTasks(author, executor, page, size);
        return ResponseEntity.ok(tasks.getContent());
    }
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto) {
        TaskDto createdTask = taskService.createTask(taskDto);
        return ResponseEntity.ok(createdTask);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @taskSecurityService.isExecutor(#id)")
    public ResponseEntity<TaskDto> updateTask(@PathVariable Long id, @RequestBody TaskDto taskDto) {
        taskDto.setId(id);
        TaskDto updateTask = taskService.updateTask(taskDto);
        return ResponseEntity.ok(updateTask);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TaskDto> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
