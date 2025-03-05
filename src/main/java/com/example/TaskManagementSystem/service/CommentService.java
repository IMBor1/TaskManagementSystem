package com.example.TaskManagementSystem.service;

import com.example.TaskManagementSystem.model.dto.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(Long taskId, CommentDto commentDto);

    CommentDto updateComment(Long commentId, CommentDto commentDto);

    void deleteComment(Long commentId);

    List<CommentDto> getCommentsByTaskId(Long taskId);
}
