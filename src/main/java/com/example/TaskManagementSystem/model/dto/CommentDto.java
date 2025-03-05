package com.example.TaskManagementSystem.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
@NoArgsConstructor
@Data
public class CommentDto {
    private Long id;

    @NonNull
    private String text;

    private Long authorId;
}
