package com.example.TaskManagementSystem.controller;

import com.example.TaskManagementSystem.model.dto.TaskDto;
import com.example.TaskManagementSystem.repository.TaskRepository;
import com.example.TaskManagementSystem.service.TaskService;
import com.example.TaskManagementSystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/allTasks")
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
