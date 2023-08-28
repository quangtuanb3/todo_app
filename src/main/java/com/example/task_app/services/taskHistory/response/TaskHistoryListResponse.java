package com.example.task_app.services.taskHistory.response;

import com.example.task_app.model.enumeration.TaskStatus;
import com.example.task_app.model.enumeration.TaskType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TaskHistoryListResponse {
    private String id;
    private String title;
    private String description;
    private LocalDateTime start;
    private LocalDateTime end;
    private TaskType type;
    private TaskStatus status;
}
