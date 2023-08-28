package com.example.task_app.services.task.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
@NoArgsConstructor
public class TaskSaveRequest {
    private String id;
    private String title;
    private String description;
    private String start;
    private String end;
}
