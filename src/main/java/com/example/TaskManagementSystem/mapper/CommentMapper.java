package com.example.TaskManagementSystem.mapper;

import com.example.TaskManagementSystem.model.dto.CommentDto;
import com.example.TaskManagementSystem.model.entity.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    public CommentDto toDto(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setText(comment.getText());
        dto.setAuthorId(comment.getAuthor() != null ? comment.getAuthor().getId() : null);
        return dto;
    }

    public Comment toEntity(CommentDto dto) {
        Comment comment = new Comment();
        comment.setId(dto.getId());
        comment.setText(dto.getText());
        return comment;
    }
}
