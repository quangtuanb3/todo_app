package com.example.task_app.model;


import com.example.task_app.model.enumeration.TaskStatus;
import com.example.task_app.model.enumeration.TaskType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "task_histories")
@Where(clause = "deleted = 0")
@SQLDelete(sql = "UPDATE task_histories SET `deleted` = 1 WHERE (`id` = ?);")
public class TaskHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private LocalDateTime start;
    private LocalDateTime end;
    private LocalDate created;
    private boolean deleted = false;
    @Enumerated(value = EnumType.STRING)
    private TaskStatus status;

    @Enumerated(value = EnumType.STRING)
    private TaskType type;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Task task;

    @PrePersist
    public void setupBeforeInsert() {
        created = LocalDate.now();
    }
}
