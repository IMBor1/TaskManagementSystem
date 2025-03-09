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

/**
 * Реализация сервиса для управления задачами.
 * Предоставляет методы для создания, обновления, удаления и получения задач.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserRepository userRepository;

    /**
     * Получает список всех задач с пагинацией.
     * Фильтрует задачи по email автора и исполнителя.
     *
     * @param author email автора задачи
     * @param executor email исполнителя задачи
     * @param page номер страницы
     * @param size размер страницы
     * @return страница с задачами
     */
    @Override
    public Page<TaskDto> getAllTasks(String author, String executor, int page, int size) {
        log.info("Getting all tasks");
        Pageable pageable = PageRequest.of(page, size);
        Page<Task> tasks = taskRepository.findByAuthorEmailOrExecutorEmail(author, executor, pageable);
        return tasks.map(taskMapper::toDto);
    }

    /**
     * Создает новую задачу.
     *
     * @param taskDto данные новой задачи
     * @return созданная задача
     * @throws NotFoundException если автор или исполнитель не найдены
     */
    @Override
    public TaskDto createTask(TaskDto taskDto) {
        log.info("Creating task: {}", taskDto);
        
        User author = userRepository.findById(taskDto.getAuthorId())
            .orElseThrow(() -> new NotFoundException("Author with ID " + taskDto.getAuthorId() + " not found"));
            
        User executor = userRepository.findById(taskDto.getExecutorId())
            .orElseThrow(() -> new NotFoundException("Executor with ID " + taskDto.getExecutorId() + " not found"));
            
        Task task = taskMapper.toEntity(taskDto);
        task.setAuthor(author);
        task.setExecutor(executor);
        Task savedTask = taskRepository.save(task);
        log.info("Task created successfully with ID: {}", savedTask.getId());
        return taskMapper.toDto(savedTask);
    }

    /**
     * Обновляет существующую задачу.
     *
     * @param taskDto данные для обновления задачи
     * @return обновленная задача
     * @throws NotFoundException если задача не найдена
     */
    @Override
    public TaskDto updateTask(TaskDto taskDto) {
        log.info("Updating task: {}", taskDto);
        Task task = taskRepository.findById(taskDto.getId())
                .orElseThrow(() -> new NotFoundException("Task not found"));
        Task updateTask = taskMapper.toEntity(taskDto);
        task.setTitle(updateTask.getTitle());
        task.setExecutor(updateTask.getExecutor());
        task.setDescription(updateTask.getDescription());
        task.setStatus(updateTask.getStatus());
        task.setComments(updateTask.getComments());
        task.setPriority(updateTask.getPriority());
        return taskMapper.toDto(taskRepository.save(task));
    }

    /**
     * Удаляет задачу по идентификатору.
     *
     * @param taskId идентификатор задачи
     */
    @Override
    public void deleteTask(Long taskId) {
        log.info("Deleting task with ID: {}", taskId);
        taskRepository.deleteById(taskId);
    }
}