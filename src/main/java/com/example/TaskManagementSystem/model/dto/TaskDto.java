package com.example.TaskManagementSystem.model.dto;

import com.example.TaskManagementSystem.model.enums.Priority;
import com.example.TaskManagementSystem.model.enums.Status;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

@Getter
@Setter
public class TaskDto {
    private Long id;

    @NonNull
    private String title;

    private String description;

    @NonNull
    private Status status;

    @NonNull
    private Priority priority;

    private Long authorId;
    private Long executorId;
}

