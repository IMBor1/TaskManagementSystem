package com.example.TaskManagementSystem.controller;

import com.example.TaskManagementSystem.model.dto.TaskDto;
import com.example.TaskManagementSystem.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;


    @GetMapping("/all")
    public ResponseEntity<List<TaskDto>> getAllTasks(@RequestParam String author,
                                                     @RequestParam String executor,
                                                     @RequestParam int page,
                                                     @RequestParam int size) {
        Page<TaskDto> tasks = taskService.getAllTasks(author, executor, page, size);
        return ResponseEntity.ok(tasks.getContent());
    }
    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto) {
        TaskDto createdTask = taskService.createTask(taskDto);
        return ResponseEntity.ok(createdTask);
    }
    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable String id, @RequestBody TaskDto taskDto) {
        TaskDto updateTask = taskService.updateTask(taskDto);
        return ResponseEntity.ok(updateTask);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<TaskDto> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
