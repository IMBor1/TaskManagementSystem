package com.example.TaskManagementSystem.model.entity;

import com.example.TaskManagementSystem.model.enums.Priority;
import com.example.TaskManagementSystem.model.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.NonNull;

import java.util.List;
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private Priority priority;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;
    @ManyToOne
    @JoinColumn(name = "executor_id")
    private User executor;
    @OneToMany(mappedBy = "task",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;


}
