package com.example.TaskManagementSystem.service;

import com.example.TaskManagementSystem.exception.NotFoundException;
import com.example.TaskManagementSystem.mapper.CommentMapper;
import com.example.TaskManagementSystem.model.dto.CommentDto;
import com.example.TaskManagementSystem.model.entity.Comment;
import com.example.TaskManagementSystem.model.entity.Task;
import com.example.TaskManagementSystem.repository.CommentRepository;
import com.example.TaskManagementSystem.repository.TaskRepository;
import com.example.TaskManagementSystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
    public class CommentServiceImpl implements CommentService {
        private final CommentRepository commentRepository;

        private TaskRepository taskRepository;

        private UserRepository userRepository;

        private CommentMapper commentMapper;

        @Override
        public CommentDto createComment(Long taskId, CommentDto commentDto) {
            Task task = taskRepository.findById(taskId)
                    .orElseThrow(() -> new NotFoundException("Task not found"));
            Comment comment = commentMapper.toEntity(commentDto);
            comment.setTask(task);
            comment.setAuthor(userRepository.findById(commentDto.getAuthorId())
                    .orElseThrow(() -> new NotFoundException("Task not found")));
            Comment savedComment = commentRepository.save(comment);
            return commentMapper.toDto(savedComment);
        }

        @Override
        public CommentDto updateComment(Long commentId, CommentDto commentDto) {
            Comment comment = commentRepository.findById(commentId)
                    .orElseThrow(() -> new NotFoundException("Task not found"));
            Comment updatedComment = commentMapper.toEntity(commentDto);
            comment.setText(updatedComment.getText());
            Comment savedComment = commentRepository.save(comment);
            return commentMapper.toDto(savedComment);
        }

        @Override
        public void deleteComment(Long commentId) {
            commentRepository.deleteById(commentId);
        }

        @Override
        public List<CommentDto> getCommentsByTaskId(Long taskId) {
            List<Comment> comments = commentRepository.findByTaskId(taskId);
            List<CommentDto> commentDtos = new ArrayList<>();
            for (Comment comment : comments) {
                commentDtos.add(commentMapper.toDto(comment));
            }
            return commentDtos;
        }
}
