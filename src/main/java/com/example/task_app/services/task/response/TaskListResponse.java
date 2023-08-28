package com.example.task_app.services.task.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
public class TaskListResponse {
    private String id;
    private String title;
    private String description;
    private LocalTime start;
    private LocalTime end;
}
