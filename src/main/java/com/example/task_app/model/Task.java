package com.example.task_app.model;


import com.example.task_app.model.enumeration.TaskStatus;
import com.example.task_app.model.enumeration.TaskType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    private Long id;
    private String title;
    private String description;
    private LocalTime start;
    private LocalTime end;
    private TaskStatus status;
    private TaskType type;

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }

}
