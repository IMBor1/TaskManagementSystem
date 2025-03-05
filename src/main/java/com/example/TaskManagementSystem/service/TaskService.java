package com.example.TaskManagementSystem.service;

import com.example.TaskManagementSystem.model.dto.TaskDto;
import org.springframework.data.domain.Page;

public interface TaskService {
    Page<TaskDto> getAllTasks(String author, String executor, int page, int size);

    TaskDto createTask(TaskDto taskDto);

    TaskDto updateTask(TaskDto taskDto);

    void deleteTask(Long taskId);
}
