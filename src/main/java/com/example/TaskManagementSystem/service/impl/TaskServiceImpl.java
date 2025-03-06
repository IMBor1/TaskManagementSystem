package com.example.TaskManagementSystem.service.impl;

import com.example.TaskManagementSystem.exception.NotFoundException;
import com.example.TaskManagementSystem.mapper.TaskMapper;
import com.example.TaskManagementSystem.model.dto.TaskDto;
import com.example.TaskManagementSystem.model.entity.Task;
import com.example.TaskManagementSystem.model.entity.User;
import com.example.TaskManagementSystem.repository.TaskRepository;
import com.example.TaskManagementSystem.repository.UserRepository;
import com.example.TaskManagementSystem.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
@Slf4j
@RequiredArgsConstructor
@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserRepository userRepository;

    @Override
    public Page<TaskDto> getAllTasks(String author, String executor, int page, int size) {
        log.info("getAllTasks");
        Pageable pageable = PageRequest.of(page, size);
        Page<Task> tasks = taskRepository.findByAuthorEmailOrExecutorEmail(author, executor, pageable);
        return tasks.map(taskMapper::toDto);
    }


    @Override
    public TaskDto createTask(TaskDto taskDto) {
        log.info("Creating task: {}", taskDto);
        
        // Проверяем существование автора
        User author = userRepository.findById(taskDto.getAuthorId())
            .orElseThrow(() -> new NotFoundException("Author with ID " + taskDto.getAuthorId() + " not found"));
            
        // Проверяем существование исполнителя
        User executor = userRepository.findById(taskDto.getExecutorId())
            .orElseThrow(() -> new NotFoundException("Executor with ID " + taskDto.getExecutorId() + " not found"));
            
        Task task = taskMapper.toEntity(taskDto);
        task.setAuthor(userRepository.findById(taskDto.getAuthorId()).orElseThrow(() ->
                new NotFoundException("Not Found")));
        task.setExecutor(userRepository.findById(taskDto.getExecutorId()).orElseThrow(() ->
                new NotFoundException("Not Found")));
                task = taskRepository.save(task);
                log.info("Task created successfully with ID: {}", task.getId());
                return taskMapper.toDto(task);
    }
    @Override
    public TaskDto updateTask(TaskDto taskDto) {
        log.info("updateTask");
        Task task = taskRepository.findById(taskDto.getId()).orElseThrow(() ->
                new NotFoundException("Not Found"));
        Task updateTask = taskMapper.toEntity(taskDto);
        task.setTitle(updateTask.getTitle());
        task.setExecutor(updateTask.getExecutor());
        task.setDescription(updateTask.getDescription());
        task.setStatus(updateTask.getStatus());
        task.setComments(updateTask.getComments());
        task.setPriority(updateTask.getPriority());
        return taskMapper.toDto(taskRepository.save(task));
    }
    @Override
    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
        log.info("deleteTask");
    }
}