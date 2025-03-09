package com.example.TaskManagementSystem.service;

import com.example.TaskManagementSystem.exception.NotFoundException;
import com.example.TaskManagementSystem.mapper.TaskMapper;
import com.example.TaskManagementSystem.model.dto.TaskDto;
import com.example.TaskManagementSystem.model.entity.Task;
import com.example.TaskManagementSystem.model.entity.User;
import com.example.TaskManagementSystem.model.enums.Priority;
import com.example.TaskManagementSystem.model.enums.Role;
import com.example.TaskManagementSystem.model.enums.Status;
import com.example.TaskManagementSystem.repository.TaskRepository;
import com.example.TaskManagementSystem.repository.UserRepository;
import com.example.TaskManagementSystem.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskServiceImpl taskService;

    private User author;
    private User executor;
    private Task task;
    private TaskDto taskDto;

    @BeforeEach
    void setUp() {
        author = new User();
        author.setId(1L);
        author.setEmail("author@test.com");
        author.setRole(Role.ADMIN);

        executor = new User();
        executor.setId(2L);
        executor.setEmail("executor@test.com");
        executor.setRole(Role.USER);

        task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");
        task.setDescription("Test Description");
        task.setStatus(Status.WAITING);
        task.setPriority(Priority.HIGH);
        task.setAuthor(author);
        task.setExecutor(executor);

        taskDto = new TaskDto();
        taskDto.setId(1L);
        taskDto.setTitle("Test Task");
        taskDto.setDescription("Test Description");
        taskDto.setStatus(Status.WAITING);
        taskDto.setPriority(Priority.HIGH);
        taskDto.setAuthorId(1L);
        taskDto.setExecutorId(2L);
    }

    @Test
    void createTask_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(author));
        when(userRepository.findById(2L)).thenReturn(Optional.of(executor));
        when(taskMapper.toEntity(taskDto)).thenReturn(task);
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        when(taskMapper.toDto(task)).thenReturn(taskDto);

        TaskDto result = taskService.createTask(taskDto);

        assertNotNull(result);
        assertEquals(taskDto.getTitle(), result.getTitle());
        assertEquals(taskDto.getStatus(), result.getStatus());
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void createTask_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> taskService.createTask(taskDto));
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void getAllTasks_Success() {
        Page<Task> taskPage = new PageImpl<>(List.of(task));
        when(taskRepository.findByAuthorEmailOrExecutorEmail(anyString(), anyString(), any(PageRequest.class)))
                .thenReturn(taskPage);
        when(taskMapper.toDto(task)).thenReturn(taskDto);

        Page<TaskDto> result = taskService.getAllTasks("author@test.com", "executor@test.com", 0, 10);

        assertNotNull(result);
        assertFalse(result.getContent().isEmpty());
        assertEquals(1, result.getContent().size());
    }
} 