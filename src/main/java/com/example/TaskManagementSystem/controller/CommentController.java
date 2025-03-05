package com.example.TaskManagementSystem.controller;

import com.example.TaskManagementSystem.model.dto.CommentDto;
import com.example.TaskManagementSystem.model.entity.Comment;
import com.example.TaskManagementSystem.service.CommentService;
import com.example.TaskManagementSystem.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tasks/{taskId}/comments/")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDto> addComment(@PathVariable Long taskId, @RequestBody CommentDto commentDto) {
        CommentDto newComment = commentService.createComment(taskId, commentDto);
        return new ResponseEntity<>(newComment, HttpStatus.CREATED);
    }
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long taskId,
                                                    @PathVariable Long commentId,
                                                    @RequestBody CommentDto commentDto) {
        CommentDto updateComment = commentService.updateComment(commentId, commentDto);
        return new ResponseEntity<>(updateComment, HttpStatus.OK);
    }
    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Long taskId, @PathVariable Long commentId) {
        commentService.deleteComment(commentId);
    }
    @GetMapping
    public ResponseEntity<List<CommentDto>> getAllComments(@PathVariable Long taskId) {
        List<CommentDto> comments = commentService.getCommentsByTaskId(taskId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
}
