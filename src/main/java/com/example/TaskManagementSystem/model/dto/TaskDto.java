package com.example.TaskManagementSystem.model.dto;

import com.example.TaskManagementSystem.model.enums.Priority;
import com.example.TaskManagementSystem.model.enums.Status;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@NoArgsConstructor
public class TaskDto {
    private Long id;

    @NonNull
    private String title;

    private String description;

    @NonNull
    private Status status;

    @NonNull
    private Priority priority;
    @NonNull
    private Long authorId;
    @NonNull
    private Long executorId;
}

